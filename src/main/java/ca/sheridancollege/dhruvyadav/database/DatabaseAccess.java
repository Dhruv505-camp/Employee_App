package ca.sheridancollege.dhruvyadav.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.dhruvyadav.beans.Appointment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DatabaseAccess {

    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    // Insert an appointment
    public void insertAppointment(Appointment appointment) {
        String query = "INSERT INTO appointment (firstName, email, appointmentDate, appointmentTime) VALUES (:firstName, :email, :appointmentDate, :appointmentTime)";
        SqlParameterSource params = new BeanPropertySqlParameterSource(appointment);
        jdbc.update(query, params);
    }

    // Get all appointments
    public List<Appointment> getAllAppointments() {
        String query = "SELECT * FROM appointment";
        return jdbc.query(query, new RowMapper<Appointment>() {
            @Override
            public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
                Appointment appointment = new Appointment();
                appointment.setId(rs.getLong("id"));
                appointment.setFirstName(rs.getString("firstName"));
                appointment.setEmail(rs.getString("email"));
                appointment.setAppointmentDate(rs.getDate("appointmentDate").toLocalDate());
                appointment.setAppointmentTime(rs.getTime("appointmentTime").toLocalTime());
                return appointment;
            }
        });
    }

    // Get an appointment by ID
    public Appointment getAppointmentById(Long id) {
        String query = "SELECT * FROM appointment WHERE id = :id";
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        return jdbc.queryForObject(query, params, new RowMapper<Appointment>() {
            @Override
            public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
                Appointment appointment = new Appointment();
                appointment.setId(rs.getLong("id"));
                appointment.setFirstName(rs.getString("firstName"));
                appointment.setEmail(rs.getString("email"));
                appointment.setAppointmentDate(rs.getDate("appointmentDate").toLocalDate());
                appointment.setAppointmentTime(rs.getTime("appointmentTime").toLocalTime());
                return appointment;
            }
        });
    }

    // Delete an appointment by ID
    public void deleteAppointmentById(Long id) {
        String query = "DELETE FROM appointment WHERE id = :id";
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        jdbc.update(query, params);
    }
}

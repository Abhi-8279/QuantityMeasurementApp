package com.quantityApp.repository;

import com.quantityApp.entity.QuantityMeasurementEntity;
import com.quantityApp.exception.DatabaseException;
import com.quantityApp.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuantityMeasurementDatabaseRepository implements IQuantityMeasurementRepository {

	@Override
	public void save(QuantityMeasurementEntity entity) {

	    String sql = "INSERT INTO quantity_measurements(operation, measurement_type, unit1, value1, unit2, value2, result) VALUES(?,?,?,?,?,?,?)";

	    Connection conn = null;

	    try {

	        conn = ConnectionPool.getConnection();
	        PreparedStatement ps = conn.prepareStatement(sql);

	        ps.setString(1, entity.getOperation());
	        ps.setString(2, entity.getMeasurementType());
	        ps.setString(3, entity.getUnit1());
	        ps.setDouble(4, entity.getValue1());
	        ps.setString(5, entity.getUnit2());
	        ps.setDouble(6, entity.getValue2());
	        ps.setBoolean(7, entity.getResult());

	        ps.executeUpdate();

	    } catch (SQLException e) {
	        throw new DatabaseException("Error saving measurement", e);

	    } finally {
	        ConnectionPool.releaseConnection(conn);
	    }
	}

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {

        List<QuantityMeasurementEntity> list = new ArrayList<>();
        String sql = "SELECT * FROM quantity_measurements";

        Connection conn = null;

        try {

            conn = ConnectionPool.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

                entity.setId(rs.getInt("id"));
                entity.setOperation(rs.getString("operation"));
                entity.setMeasurementType(rs.getString("measurement_type"));
                entity.setValue1(rs.getDouble("value1"));
                entity.setValue2(rs.getDouble("value2"));
                entity.setResult(rs.getBoolean("result"));

                list.add(entity);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving measurements", e);

        } finally {
            ConnectionPool.releaseConnection(conn);
        }

        return list;
    }

    @Override
    public void deleteAll() {

        String sql = "DELETE FROM quantity_measurements";

        Connection conn = null;

        try {

            conn = ConnectionPool.getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            throw new DatabaseException("Error deleting measurements", e);

        } finally {
            ConnectionPool.releaseConnection(conn);
        }
    }

    @Override
    public List<QuantityMeasurementEntity> findAll() {
        return getAllMeasurements();
    }
}
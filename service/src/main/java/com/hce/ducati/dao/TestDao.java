package com.hce.ducati.dao;

import java.util.Date;
import java.util.List;

import com.hce.ducati.o.OneSubTestDynamicFieldsDto;
import com.hce.ducati.o.SubTestDto;
import com.hce.ducati.o.UestDto;
import com.hce.ducati.o.UserDto;
import com.quincy.sdk.DynamicField;
import com.quincy.sdk.annotation.jdbc.ExecuteQuery;
import com.quincy.sdk.annotation.jdbc.ExecuteQueryWIthDynamicColumns;
import com.quincy.sdk.annotation.jdbc.ExecuteUpdate;
import com.quincy.sdk.annotation.jdbc.FindDynamicFields;
import com.quincy.sdk.annotation.jdbc.JDBCDao;

@JDBCDao
public interface TestDao {
	@ExecuteQuery(sql = "SELECT id AS id_str,id,name,mobile_phone,creation_time FROM s_user WHERE id=?", returnItemType = UserDto.class)
	public UserDto find(Long id);
	@ExecuteUpdate(sql = "UPDATE s_user SET mobile_phone=? WHERE id=?")
	public int upate(String mobilePhone, Long id);
	@ExecuteUpdate(sql = "UPDATE   sub_test  SET fff=?,eee=? WHERE fff=?", withHistory = true)
	public int testUpateRecord(int fffTo, String eee, int fffFrom);
	@ExecuteUpdate(sql = "UPDATE   sub_test  SET eee=CONCAT(eee, ?) WHERE fff=?", withHistory = true, selectionSql = "SELECT id,eee FROM sub_test WHERE fff=?")
	public int testUpateRecord2(String eee, int fff);
	@ExecuteUpdate(sql = "UPDATE test t INNER JOIN zelation z ON t.id=z.test_id INNER JOIN uest u ON z.uest_id=u.id SET t.aaa=CONCAT(t.aaa,?),u.ccc=CONCAT(u.ccc,?),z.ggg=z.ggg+? WHERE u.ddd=?", withHistory = true, 
			selectionSql = "SELECT t.aaa,u.id AS uid,z.ggg,t.id AS tid,u.ccc,z.id AS zid FROM test t INNER JOIN zelation z ON t.id=z.test_id INNER JOIN uest u ON z.uest_id=u.id WHERE u.ddd=?")
	public int testUpateRecord3(String aaa, String ccc, int ggg, int ddd);
	@ExecuteUpdate(sql = "UPDATE test t INNER JOIN sub_test s ON t.id=s.test_id SET t.aaa=CONCAT(t.aaa,?),t.bbb=t.bbb+?,s.eee=CONCAT(s.eee,?),s.bbdd=s.bbdd+?,s.ddd1=?,s.ddd2=?,s.ddd3=? WHERE s.fff=?", withHistory = true, 
			selectionSql = "SELECT t.bbb,t.aaa AS ta,t.id AS tid,s.eee,s.id,s.bbdd,s.ddd1,s.ddd2,s.ddd3 FROM test t INNER JOIN sub_test s ON t.id=s.test_id WHERE s.fff=?")
	public int testUpateRecord4(String aaa, int bbb, String eee, float bbdd, Date ddd1, Date ddd2, Date ddd3, int fff);
	@ExecuteQueryWIthDynamicColumns(sqlFrontHalf = "SELECT s.id,s.eee,s.fff,f.id,f.name,f.sort,v.value_decimal,v.value_str FROM (SELECT * FROM sub_test LIMIT 10 OFFSET 0)", tableName = "sub_test", returnItemType = SubTestDto.class)
	public List<SubTestDto> findSubTest();
	@ExecuteQueryWIthDynamicColumns(sqlFrontHalf = "SELECT s.id,s.eee,s.fff,f.id,f.name,f.sort,v.value_decimal,v.value_str FROM (SELECT * FROM sub_test WHERE id=?)", tableName = "sub_test", returnItemType = SubTestDto.class)
	public OneSubTestDynamicFieldsDto findOneSubTest(String id);
	@ExecuteQueryWIthDynamicColumns(sqlFrontHalf = "SELECT s.id,s.eee,s.fff,f.id,f.name,f.sort,v.value_decimal,v.value_str FROM (SELECT * FROM sub_test WHERE id=?)", tableName = "sub_test", returnItemType = SubTestDto.class)
	public SubTestDto findOneSubTest2(String id);
	@FindDynamicFields("sub_test")
	public List<DynamicField> findSubTestDynamicFields();
	@ExecuteUpdate(sql = "UPDATE uest SET ddd=ddd+1 WHERE id=?")
	public int updateUest(Long id);
	@ExecuteUpdate(sql = "INSERT INTO uest(ccc, ddd) VALUES ('bcdefg', 21);")
	public int insertUest();
	@ExecuteQuery(sql = "SELECT * FROM uest WHERE ddd BETWEEN ? AND ?;", returnItemType = UestDto.class)
	public List<UestDto> findUest(Integer start, Integer end);
	@ExecuteQuery(sql = "SELECT * FROM uest WHERE id=?;", returnItemType = UestDto.class)
	public UestDto findUest(Long id);
}
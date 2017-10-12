package com.esite.ops.statistics.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.esite.ops.statistics.entity.StatisticsEntity;

public interface StatisticsDao extends CrudRepository<StatisticsEntity, String> {

	/**
	 * 认证百分比统计
	 * <br>已经修改为vw_health_info视图
	 * @param cycleId
	 * @return
	 */
	//@Query(value="select CONCAT(truncate(res.bd/res.total*100,2),'%') bd_bfb,CONCAT(truncate(res.wd/res.total*100,2),'%') wd_bfb,CONCAT(truncate(res.wrz/res.total*100,2),'%') wrz_bfb,CONCAT(truncate(res.yrz/res.total*100,2),'%') yrz_bfb,(select o.name from sys_security_org o where o.id=res.ROOT_AREA_ID) area_name,res.* from (select t.ROOT_AREA_ID,count(t.id) total,sum(case when t.type='1' then 1 else 0 end ) bd,sum(case when t.type='2' then 1 else 0 end ) wd,sum(case when (h.VERIFY_STATE='-1' or h.VERIFY_STATE is null) then 1 else 0 end ) wrz,sum(case when h.VERIFY_STATE!='-1' then 1 else 0 end ) yrz,sum(case when h.VERIFY_STATE!='-1' and t.type='1' then 1 else 0 end ) bd_yrz,sum(case when h.VERIFY_STATE!='-1' and t.type='2' then 1 else 0 end ) wd_yrz,0 display_order from old_person t left join vw_health_info h on t.id=h.OLD_PERSON_ID and h.CYCLE_ID=?1 where t.`status`='' group by t.ROOT_AREA_ID with ROLLUP UNION select org.id,0,0,0,0,0,0,0,org.display_order from sys_security_org org where org.type='z' AND (org.STATUS is null or org.`status`='')) res group by res.ROOT_AREA_ID order by sum(res.display_order)",nativeQuery=true)
	@Query(value="select CONCAT(truncate(round(res.bd/res.total*100,2),2),'%') bd_bfb,CONCAT(truncate(round(res.wd/res.total*100,2),2),'%') wd_bfb,CONCAT(truncate(res.wrz/res.total*100,2),'%') wrz_bfb,CONCAT(truncate(res.yrz/res.total*100,2),'%') yrz_bfb,(select o.name from sys_security_org o where o.id=res.ROOT_AREA_ID) area_name,res.* from (select t.ROOT_AREA_ID,count(t.id) total,sum(case when t.type='1' then 1 else 0 end ) bd,sum(case when t.type='2' then 1 else 0 end ) wd,sum(case when (h.VERIFY_STATE='-1' or h.VERIFY_STATE is null) then 1 else 0 end ) wrz,sum(case when h.VERIFY_STATE!='-1' then 1 else 0 end ) yrz,sum(case when h.VERIFY_STATE!='-1' and t.type='1' then 1 else 0 end ) bd_yrz,sum(case when h.VERIFY_STATE!='-1' and t.type='2' then 1 else 0 end ) wd_yrz,0 display_order from old_person t left join vw_health_info h on t.id=h.OLD_PERSON_ID and h.CYCLE_ID=?1 where t.`status`='' group by t.ROOT_AREA_ID with ROLLUP UNION select org.id,0,0,0,0,0,0,0,org.display_order from sys_security_org org where org.type='z' AND (org.STATUS is null or org.`status`='')) res group by res.ROOT_AREA_ID order by sum(res.display_order)",nativeQuery=true)
	public List<Map<String, Object>> rzbfb(String cycleId);
	
	/**
	 * 认证百分比统计(社区级别和村级别)
	 * <br>已经修改为vw_health_info视图
	 * @param cycleId
	 * @return
	 */
	//@Query(value="SELECT CONCAT(TRUNCATE(res.bd/res.total*100,2),'%') bd_bfb,CONCAT(TRUNCATE(res.wd/res.total*100,2),'%') wd_bfb,CONCAT(TRUNCATE(res.wrz/res.total*100,2), '%') wrz_bfb,CONCAT(TRUNCATE(res.yrz/res.total*100, 2),'%' ) yrz_bfb,( SELECT o. NAME FROM sys_security_org o WHERE o.id = res.AREA_ID ) area_name, res.* FROM ( SELECT t.AREA_ID, count(t.id) total, sum( CASE WHEN t.type = '1' THEN 1 ELSE 0 END ) bd, sum( CASE WHEN t.type = '2' THEN 1 ELSE 0 END ) wd, sum( CASE WHEN ( h.VERIFY_STATE = '-1' OR h.VERIFY_STATE IS NULL ) THEN 1 ELSE 0 END ) wrz, sum( CASE WHEN h.VERIFY_STATE != '-1' THEN 1 ELSE 0 END ) yrz, sum( CASE WHEN h.VERIFY_STATE != '-1' AND t.type = '1' THEN 1 ELSE 0 END ) bd_yrz, sum( CASE WHEN h.VERIFY_STATE != '-1' AND t.type = '2' THEN 1 ELSE 0 END ) wd_yrz, 0 display_order FROM old_person t LEFT JOIN vw_health_info h ON t.id = h.OLD_PERSON_ID AND h.CYCLE_ID = ?1 WHERE t.`status` = '' and t.ROOT_AREA_ID=?2 GROUP BY t.AREA_ID  UNION SELECT org.id, 0, 0, 0, 0, 0, 0, 0, org.display_order FROM vw_area org WHERE (org.type = 'c' or org.type='sq') and org.root_z=?2 AND ( org. STATUS IS NULL OR org.`status` = '' ) ) res GROUP BY res.AREA_ID ORDER BY sum(res.display_order)",nativeQuery=true)
	@Query(value="SELECT CONCAT(TRUNCATE(round(res.bd/res.total*100,2),2),'%') bd_bfb,CONCAT(TRUNCATE(round(res.wd/res.total*100,2),2),'%') wd_bfb,CONCAT(TRUNCATE(res.wrz/res.total*100,2), '%') wrz_bfb,CONCAT(TRUNCATE(res.yrz/res.total*100, 2),'%' ) yrz_bfb,( SELECT o. NAME FROM sys_security_org o WHERE o.id = res.AREA_ID ) area_name, res.* FROM ( SELECT t.AREA_ID, count(t.id) total, sum( CASE WHEN t.type = '1' THEN 1 ELSE 0 END ) bd, sum( CASE WHEN t.type = '2' THEN 1 ELSE 0 END ) wd, sum( CASE WHEN ( h.VERIFY_STATE = '-1' OR h.VERIFY_STATE IS NULL ) THEN 1 ELSE 0 END ) wrz, sum( CASE WHEN h.VERIFY_STATE != '-1' THEN 1 ELSE 0 END ) yrz, sum( CASE WHEN h.VERIFY_STATE != '-1' AND t.type = '1' THEN 1 ELSE 0 END ) bd_yrz, sum( CASE WHEN h.VERIFY_STATE != '-1' AND t.type = '2' THEN 1 ELSE 0 END ) wd_yrz, 0 display_order FROM old_person t LEFT JOIN vw_health_info h ON t.id = h.OLD_PERSON_ID AND h.CYCLE_ID = ?1 WHERE t.`status` = '' and t.ROOT_AREA_ID=?2 GROUP BY t.AREA_ID  UNION SELECT org.id, 0, 0, 0, 0, 0, 0, 0, org.display_order FROM vw_area org WHERE (org.type = 'c' or org.type='sq') and org.root_z=?2 AND ( org. STATUS IS NULL OR org.`status` = '' ) ) res GROUP BY res.AREA_ID ORDER BY sum(res.display_order)",nativeQuery=true)
	public List<Map<String, Object>> rzbfb(String cycleId,String areaId);

	/**
	 * 监控认证统计
	 * <br>已修改为vw_health_info视图
	 * @param cycleId
	 * @return
	 */
	@Query(value="SELECT (SELECT o. NAME FROM sys_security_org o WHERE o.id = res.ROOT_AREA_ID) area_name,res.* FROM (SELECT t.ROOT_AREA_ID,count(t.id) total,sum(CASE WHEN t.type = '1' THEN CASE WHEN (h.VERIFY_STATE = '-1' OR h.VERIFY_STATE IS NULL) THEN 1 ELSE 0 END ELSE 0 END) bd_wrz,sum(CASE WHEN t.type = '1' THEN CASE WHEN (h.VERIFY_STATE != '-1') THEN 1 ELSE 0 END ELSE 0 END ) bd_yrz,sum(CASE WHEN t.type = '2' THEN CASE WHEN (h.VERIFY_STATE = '-1' OR h.VERIFY_STATE IS NULL) THEN 					1 				ELSE 					0 				END 				ELSE 					0 				END 			) wd_wrz, 			sum( 				CASE 				WHEN t.type = '2' THEN 					CASE 				WHEN (h.VERIFY_STATE != '-1') THEN 					1 				ELSE 					0 				END 				ELSE 					0 				END 			) wd_yrz, 			0 display_order 		FROM 			old_person t 		LEFT JOIN vw_health_info h ON t.id = h.OLD_PERSON_ID 		and h.CYCLE_ID=?1 		WHERE 			t.`status` = '' 		GROUP BY 			t.ROOT_AREA_ID 		UNION ALL 			SELECT 				o.id, 				0, 				0, 				0, 				0, 				0, 				o.display_order 			FROM 				sys_security_org o 			WHERE 				o.type = 'z' 	) res GROUP BY 	res.ROOT_AREA_ID ORDER BY 	res.display_order",nativeQuery=true)
	public List<Map<String, Object>> jkrz(String cycleId);
	
	/**
	 * 
	 * <br>已修改为vw_health_info视图
	 * @param cycleId
	 * @return
	 */
	//@Query(value="select (select count(h.id) from vw_health_info h where h.OLD_PERSON_ID=op.id and h.VERIFY_STATE!='-1' and h.cycle_id=?1) yrz, (select count(h.id) from vw_health_info h where h.OLD_PERSON_ID=op.id and h.VERIFY_STATE='-1' and h.cycle_id=?1) dsh,count(op.id) total from old_person op where op.status=''",nativeQuery=true)
	@Query(value="SELECT IFNULL(sum(case when h.VERIFY_STATE != '-1' then 1 else 0 end ),0) yrz,IFNULL(sum(case when h.VERIFY_STATE = '-1' then 1 else 0 end ),0) dsh,count(op.id) total FROM old_person op left join vw_health_info h on op.id=h.OLD_PERSON_ID and h.CYCLE_ID=?1 where op.`status`=''",nativeQuery=true)
	public Map<String, Object> gktj(String id);

	/**
	 * 
	 * <br>已修改为vw_health_info视图
	 * @param cycleId
	 * @return
	 */
	@Query(value="SELECT IFNULL(sum(case when h.VERIFY_STATE != '-1' then 1 else 0 end ),0) yrz,IFNULL(sum(case when h.VERIFY_STATE = '-1' then 1 else 0 end ),0) dsh,count(op.id) total FROM old_person op left join vw_health_info h on op.id=h.OLD_PERSON_ID and h.CYCLE_ID=?1 where op.`status`='' and op.area_id=?2",nativeQuery=true)
	public Map<String, Object> gktjIsLeaf(String id, String areaId);

	/**
	 * 
	 * <br>已修改为vw_health_info视图
	 * @param cycleId
	 * @return
	 */
	@Query(value="SELECT IFNULL(sum(case when h.VERIFY_STATE != '-1' then 1 else 0 end ),0) yrz,IFNULL(sum(case when h.VERIFY_STATE = '-1' then 1 else 0 end ),0) dsh,count(op.id) total FROM old_person op left join vw_health_info h on op.id=h.OLD_PERSON_ID and h.CYCLE_ID=?1 where op.`status`='' and op.root_area_id=?2",nativeQuery=true)
	public Map<String, Object> gktjIsNotLeaf(String id, String id2);

	@Query(value="select IFNULL(sum(case when op.type='1' then 1 else 0 end ),0) bd,IFNULL(sum(case when op.type='2' then 1 else 0 end ),0) wd from old_person op where op.status=''",nativeQuery=true)
	public Map<String, Object> yhzbtj();

	@Query(value="select IFNULL(sum(case when op.type='1' then 1 else 0 end ),0) bd,IFNULL(sum(case when op.type='2' then 1 else 0 end ),0) wd from old_person op where op.status='' and op.area_id=?1",nativeQuery=true)
	public Map<String, Object> yhzbIsLeaf(String id);

	@Query(value="select IFNULL(sum(case when op.type='1' then 1 else 0 end ),0) bd,IFNULL(sum(case when op.type='2' then 1 else 0 end ),0) wd from old_person op where op.status='' and op.root_area_id=?1",nativeQuery=true)
	public Map<String, Object> yhzbIsNotLeaf(String id);
	
	/**
	 * 
	 * <br>该统计为本地统计，使用health_info
	 * @param cycleId
	 * @return
	 */
	@Query(value="SELECT IFNULL(sum(case when h.VERIFY_STATE != '-1' then 1 else 0 end ),0) yrz,IFNULL(sum(case when h.VERIFY_STATE = '-1' then 1 else 0 end ),0) dsh,count(op.id) total FROM old_person op left join health_info h on op.id=h.OLD_PERSON_ID and h.CYCLE_ID=?1 where op.`status`='' and op.type='1'",nativeQuery=true)
	public Map<String, Object> bdgktj(String id);

	/**
	 * 
	 * <br>该统计为本地统计，使用health_info
	 * @param cycleId
	 * @return
	 */
	@Query(value="SELECT IFNULL(sum(case when h.VERIFY_STATE != '-1' then 1 else 0 end ),0) yrz,IFNULL(sum(case when h.VERIFY_STATE = '-1' then 1 else 0 end ),0) dsh,count(op.id) total FROM old_person op left join health_info h on op.id=h.OLD_PERSON_ID and h.CYCLE_ID=?1 where op.`status`='' and op.area_id=?2 and op.type='1'",nativeQuery=true)
	public Map<String, Object> bdgktjIsLeaf(String id, String areaId);

	/**
	 * 
	 * <br>该统计为本地统计，使用health_info
	 * @param cycleId
	 * @return
	 */
	@Query(value="SELECT IFNULL(sum(case when h.VERIFY_STATE != '-1' then 1 else 0 end ),0) yrz,IFNULL(sum(case when h.VERIFY_STATE = '-1' then 1 else 0 end ),0) dsh,count(op.id) total FROM old_person op left join health_info h on op.id=h.OLD_PERSON_ID and h.CYCLE_ID=?1 where op.`status`='' and op.root_area_id=?2 and op.type='1'",nativeQuery=true)
	public Map<String, Object> bdgktjIsNotLeaf(String id, String areaId);

	@Query(value="SELECT IFNULL(sum(case when h.VERIFY_STATE != '-1' then 1 else 0 end ),0) yrz,IFNULL(sum(case when h.VERIFY_STATE = '-1' then 1 else 0 end ),0) dsh,count(op.id) total FROM old_person op left join health_info h on op.id=h.OLD_PERSON_ID and h.CYCLE_ID=?1 where op.`status`=''",nativeQuery=true)
	public Map<String, Object> bdyrzzb(String id);
	@Query(value="SELECT IFNULL(sum(case when h.VERIFY_STATE != '-1' then 1 else 0 end ),0) yrz,IFNULL(sum(case when h.VERIFY_STATE = '-1' then 1 else 0 end ),0) dsh,count(op.id) total FROM old_person op left join health_info h on op.id=h.OLD_PERSON_ID and h.CYCLE_ID=?1 where op.`status`='' and op.area_id=?2",nativeQuery=true)
	public Map<String, Object> bdyrzzbIsLeaf(String id, String areaId);
	@Query(value="SELECT IFNULL(sum(case when h.VERIFY_STATE != '-1' then 1 else 0 end ),0) yrz,IFNULL(sum(case when h.VERIFY_STATE = '-1' then 1 else 0 end ),0) dsh,count(op.id) total FROM old_person op left join health_info h on op.id=h.OLD_PERSON_ID and h.CYCLE_ID=?1 where op.`status`='' and op.root_area_id=?2",nativeQuery=true)
	public Map<String, Object> bdyrzzbIsNotLeaf(String id, String areaId);
	
	
	@Query(value="SELECT IFNULL(sum(case when h.VERIFY_STATE != '-1' then 1 else 0 end ),0) yrz,IFNULL(sum(case when h.VERIFY_STATE = '-1' then 1 else 0 end ),0) dsh,count(op.id) total FROM old_person op left join old_person_wd_verify_info h on op.id=h.OLD_PERSON_ID and h.CYCLE_ID=?1 where op.`status`=''",nativeQuery=true)
	public Map<String, Object> wdyrzzb(String id);
	@Query(value="SELECT IFNULL(sum(case when h.VERIFY_STATE != '-1' then 1 else 0 end ),0) yrz,IFNULL(sum(case when h.VERIFY_STATE = '-1' then 1 else 0 end ),0) dsh,count(op.id) total FROM old_person op left join old_person_wd_verify_info h on op.id=h.OLD_PERSON_ID and h.CYCLE_ID=?1 where op.`status`='' and op.area_id=?2",nativeQuery=true)
	public Map<String, Object> wdyrzzbIsLeaf(String id, String areaId);
	@Query(value="SELECT IFNULL(sum(case when h.VERIFY_STATE != '-1' then 1 else 0 end ),0) yrz,IFNULL(sum(case when h.VERIFY_STATE = '-1' then 1 else 0 end ),0) dsh,count(op.id) total FROM old_person op left join old_person_wd_verify_info h on op.id=h.OLD_PERSON_ID and h.CYCLE_ID=?1 where op.`status`='' and op.root_area_id=?2",nativeQuery=true)
	public Map<String, Object> wdyrzzbIsNotLeaf(String id, String areaId);

}

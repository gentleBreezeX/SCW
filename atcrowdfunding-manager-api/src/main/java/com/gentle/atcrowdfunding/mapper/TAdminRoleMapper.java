package com.gentle.atcrowdfunding.mapper;

import com.gentle.atcrowdfunding.bean.TAdminRole;
import com.gentle.atcrowdfunding.bean.TAdminRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TAdminRoleMapper {
    long countByExample(TAdminRoleExample example);

    int deleteByExample(TAdminRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TAdminRole record);

    int insertSelective(TAdminRole record);

    List<TAdminRole> selectByExample(TAdminRoleExample example);

    TAdminRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TAdminRole record, @Param("example") TAdminRoleExample example);

    int updateByExample(@Param("record") TAdminRole record, @Param("example") TAdminRoleExample example);

    int updateByPrimaryKeySelective(TAdminRole record);

    int updateByPrimaryKey(TAdminRole record);
    
    //根据管理员的id查询拥有的角色的id集合
    List<Integer> selectRolesIdsByAdminId(Integer id);

	void assignRoleToAdmin(@Param("adminId")Integer adminId, @Param("roleIds")List<Integer> roleIds);
    
    
}
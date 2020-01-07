package com.ecut.mall.member.mapper;

import com.ecut.mall.beans.UmsMember;
import com.ecut.mall.beans.UmsMember;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
public interface UmsMemberMapper extends Mapper<UmsMember> {
    List<UmsMember> selectAllMembers();
}

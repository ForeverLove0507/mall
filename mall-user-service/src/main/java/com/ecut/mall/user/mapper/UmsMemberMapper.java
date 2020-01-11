package com.ecut.mall.user.mapper;

import com.ecut.mall.beans.UmsMember;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UmsMemberMapper extends Mapper<UmsMember> {
    List<UmsMember> selectAllMembers();
}

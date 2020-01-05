package com.ecut.mall.member.service;

import com.ecut.mall.member.beans.UmsMember;
import com.ecut.mall.member.beans.UmsMemberReceiveAddress;


import java.util.List;

public interface UmsMemberService {
    List<UmsMember> getAllMembers();

    List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId);
}

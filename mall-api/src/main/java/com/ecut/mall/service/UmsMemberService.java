package com.ecut.mall.service;

import com.ecut.mall.beans.UmsMember;
import com.ecut.mall.beans.UmsMemberReceiveAddress;


import java.util.List;

public interface UmsMemberService {
    List<UmsMember> getAllMembers();
    List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId);
}

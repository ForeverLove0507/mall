package com.ecut.mall.user.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ecut.mall.beans.UmsMember;
import com.ecut.mall.beans.UmsMemberReceiveAddress;
import com.ecut.mall.service.UmsMemberService;
import com.ecut.mall.user.mapper.UmsMemberMapper;
import com.ecut.mall.user.mapper.UmsMemberReceiveAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class UmsMemberServiceImpl implements UmsMemberService {
@Autowired
UmsMemberMapper umsMemberMapper;
@Autowired
UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;

    @Override
    public List<UmsMember> getAllMembers() {
        return umsMemberMapper.selectAll();
    }

    @Override
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId) {
//        Example e=new Example(UmsMemberReceiveAddress.class);
//        e.createCriteria().andEqualTo("memberId",memberId);
//        List<UmsMemberReceiveAddress> umsMemberReceiveAddresses = umsMemberReceiveAddressMapper.selectByExample(e);

        //封装的参数对象
        UmsMemberReceiveAddress umsMemberReceiveAddress=new UmsMemberReceiveAddress();
        umsMemberReceiveAddress.setMemberId(memberId);
        List<UmsMemberReceiveAddress> umsMemberReceiveAddresses = umsMemberReceiveAddressMapper.select(umsMemberReceiveAddress);
        return umsMemberReceiveAddresses;
    }
}

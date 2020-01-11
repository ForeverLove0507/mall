package com.ecut.mall.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ecut.mall.beans.UmsMember;
import com.ecut.mall.beans.UmsMemberReceiveAddress;
import com.ecut.mall.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UmsMemberController {
    @Reference
    UmsMemberService umsMemberService;

    @RequestMapping("getReceiveAddressByMemberId")
    @ResponseBody
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(String memberId){
        List<UmsMemberReceiveAddress> umsMemberReceiveAddresses=umsMemberService.getReceiveAddressByMemberId(memberId);
        return  umsMemberReceiveAddresses;
    }

    @RequestMapping("getAllMembers")
    @ResponseBody
    public List<UmsMember> getAllMembers(){
        return  umsMemberService.getAllMembers();
    }

}

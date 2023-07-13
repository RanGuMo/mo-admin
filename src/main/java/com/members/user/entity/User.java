package com.members.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author morangu
 * @since 2023-06-01
 */
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer id;

    /**
     * 所属店铺
     */
    private Integer shopId;

    /**
     * 部门id
     */
    private Integer deptId;

    /**
     * 创建人
     */
    private Integer createId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 员工编号
     */
    private String userNo;

    /**
     * 性别
     */
    private String sex;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态 正常，离职停用
     */
    private String status;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }
    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }
    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", shopId=" + shopId +
            ", deptId=" + deptId +
            ", createId=" + createId +
            ", name=" + name +
            ", password=" + password +
            ", userNo=" + userNo +
            ", sex=" + sex +
            ", address=" + address +
            ", phone=" + phone +
            ", remark=" + remark +
            ", status=" + status +
            ", createDate=" + createDate +
        "}";
    }
}

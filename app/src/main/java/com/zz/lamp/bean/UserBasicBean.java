package com.zz.lamp.bean;

import java.util.List;

public class UserBasicBean {
    String avatar;
    String name;
    String searchValue;// null,
    String createBy;// null,
    String createTime;// 2020-04-28 21;//36;//26,
    String updateBy;// null,
    String updateTime;// null,
    String remark;// ,
    String deptId;// 118,
    String userId;// 11,
    String parentId;// null,
    String roleId;// null,
    int permission;// 1,
    String gtCid;// null,
    String loginName;// apptest,
    String userName;// AppTest,
    String email;// apptest@test.com,
    String phonenumber;// 15145678990,
    int sex;// 0,
    String password;// d51818386c880e6b8bf8e84446c76c7b,
    String salt;// bf17b8,
    int status;// 0,
    String delFlag;// 0,
    String loginIp;// 127.0.0.1,
    String loginDate;// 2020-05-12 11;//03;//37,
    Dept dept;
    List<Role> roles;// [

    String roleIds;// null,
    String postIds;// null,
    boolean admin;// false

    public String getSearchValue() {
        return searchValue;
    }

    public String getCreateBy() {
        return createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public String getDeptId() {
        return deptId;
    }

    public String getUserId() {
        return userId;
    }

    public String getParentId() {
        return parentId;
    }

    public String getRoleId() {
        return roleId;
    }


    public String getGtCid() {
        return gtCid;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }


    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public int getPermission() {
        return permission;
    }

    public int getSex() {
        return sex;
    }

    public int getStatus() {
        return status;
    }

    public boolean isAdmin() {
        return admin;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public Dept getDept() {
        return dept;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public String getPostIds() {
        return postIds;
    }


    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public class Dept {
        String searchValue;// null,
        String createBy;// null,
        String createTime;// null,
        String updateBy;// null,
        String updateTime;// null,
        String remark;// null,
        String deptId;// 118,
        String parentId;// 100,
        String ancestors;// null,
        String deptName;// App测试,
        String deptAddress;// null,
        String platformName;// null,
        String orderNum;// 10,
        String leader;// NULL,
        String phone;// null,
        String email;// null,
        String status;// 0,
        String delFlag;// null,
        String parentName;// null,
        String systemType;// null

        public String getSearchValue() {
            return searchValue;
        }

        public String getCreateBy() {
            return createBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public String getRemark() {
            return remark;
        }

        public String getDeptId() {
            return deptId;
        }

        public String getParentId() {
            return parentId;
        }

        public String getAncestors() {
            return ancestors;
        }

        public String getDeptName() {
            return deptName;
        }

        public String getDeptAddress() {
            return deptAddress;
        }

        public String getPlatformName() {
            return platformName;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public String getLeader() {
            return leader;
        }

        public String getPhone() {
            return phone;
        }

        public String getEmail() {
            return email;
        }

        public String getStatus() {
            return status;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public String getParentName() {
            return parentName;
        }

        public String getSystemType() {
            return systemType;
        }
    }

    private class Role {
        String searchValue;// null,
        String createBy;// null,
        String createTime;// null,
        String updateBy;// null,
        String updateTime;// null,
        String remark;// null,
        String deptId;// null,
        String roleId;// 9,
        String roleName;// App Test,
        String roleKey;// appTest,
        String roleSort;// 9,
        String dataScope;// 4,
        String status;// 0,
        String delFlag;// null,
        boolean flag;// false,
        String menuIds;// null,
        String deptIds;// null,
        boolean admin;// false

        public String getSearchValue() {
            return searchValue;
        }

        public String getCreateBy() {
            return createBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public String getRemark() {
            return remark;
        }

        public String getDeptId() {
            return deptId;
        }

        public String getRoleId() {
            return roleId;
        }

        public String getRoleName() {
            return roleName;
        }

        public String getRoleKey() {
            return roleKey;
        }

        public String getRoleSort() {
            return roleSort;
        }

        public String getDataScope() {
            return dataScope;
        }

        public String getStatus() {
            return status;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public boolean isFlag() {
            return flag;
        }

        public String getMenuIds() {
            return menuIds;
        }

        public String getDeptIds() {
            return deptIds;
        }

        public boolean isAdmin() {
            return admin;
        }
    }
}

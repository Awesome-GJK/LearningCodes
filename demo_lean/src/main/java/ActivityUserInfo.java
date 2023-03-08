import java.util.List;

import lombok.Data;

/**
 * @Description: 活动用户相关信息
 * @Author: zhutao
 * @Date: 2020/6/17
 */
@Data
public class ActivityUserInfo {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 城市ID
     */
    private String cityId;

    /**
     * 归属机构ID
     */
    private String vehicleOrgId;

    /**
     * 云快充C端用户
     */
    private boolean ykcClientUser;

    /**
     * 用户标签列表
     */
    private List<String> labelIdList;

    /**
     * 用户归属运营商ID
     */
    private String operatorId;

    /**
     * 是否云快充机构
     */
    private boolean belongYkcOrg;

    /**
     * 机构类型
     */
    private String vehicleOrgType;

    /**
     * 用户类型（1个人用户 2企业用户）
     */
    private Integer orgUserType;

    public ActivityUserInfo(String userId) {
        this.userId = userId;
    }
}

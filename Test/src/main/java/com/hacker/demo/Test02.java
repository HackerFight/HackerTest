package com.hacker.demo;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hacker
 * @date：2018/9/28
 * @project project
 * @describe
 */
public class Test02 {

    public static void main(String[] args) {

        List<TreeNode> list = new ArrayList<>();
        TreeNode treeNode = new TreeNode();
        treeNode.setCurrentId("1000001");
        treeNode.setDisplayId("1000001");
        treeNode.setDisplayName("站点");
        treeNode.setOwnerId("1000001");
        treeNode.setDisplayType("40012");
        treeNode.setParentId(null);


        TreeNode treeNode2 = new TreeNode();
        treeNode2.setCurrentId("5302589687425");
        treeNode2.setDisplayId("5302589687425");
        treeNode2.setDisplayName("污染源");
        treeNode2.setOwnerId("1000001");
        treeNode2.setParentId("1000001");
        treeNode2.setDisplayType("204001");

        TreeNode treeNode3 = new TreeNode();
        treeNode3.setCurrentId("53890143001141");
        treeNode3.setDisplayId("53890143001141");
        treeNode3.setDisplayName("污染设备");
        treeNode3.setOwnerId("1000001");
        treeNode3.setParentId("5302589687425");
        treeNode3.setDisplayType("204002");

        list.add(treeNode);
        list.add(treeNode2);
        list.add(treeNode3);

        List<TreeNode> collect = list.stream().filter(item -> "40012".equals(item.getDisplayType())).collect(Collectors.toList());
        System.out.println(collect.get(0));

        String str = "1001";
        System.out.println(str.equals(String.valueOf(1001)));

    }
}


class TreeNode {

    private String currentId;

    private String displayId;

    private String displayName;

    private String displayType;

    private String parentId;

    private String ownerId;

    private String ownerName;

    public String getCurrentId() {
        return currentId;
    }

    public void setCurrentId(String currentId) {
        this.currentId = currentId;
    }

    public String getDisplayId() {
        return displayId;
    }

    public void setDisplayId(String displayId) {
        this.displayId = displayId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "currentId='" + currentId + '\'' +
                ", displayId='" + displayId + '\'' +
                ", displayName='" + displayName + '\'' +
                ", displayType='" + displayType + '\'' +
                ", parentId='" + parentId + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", ownerName='" + ownerName + '\'' +
                '}';
    }
}

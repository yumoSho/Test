<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ include file="/WEB-INF/view/include/taglibs.jspf" %>
<m:h>
    <%@ include file="/WEB-INF/view/include/elibs.jspf" %>
</m:h>
<%@include file="/WEB-INF/view/include/admin/adminHead.jspf" %>
<div class="main">
    <%@include file="/WEB-INF/view/include/admin/adminSidebar.jspf" %>
    <!-- Begin 主内容区 -->
    <div class="content">
        <!-- Begin 当前位置 -->
        <div class="position">
            <span>当前位置：</span>
            <a href="${ctx}/admin" title="首页">首页</a> &gt;
            <a href="${ctx}/admin/member/index" ; title="会员管理">会员管理</a> &gt;
            <a href="${ctx}/admin/member/index" ; title="会员管理">会员管理</a> &gt;
            编辑
        </div>
        <!-- //End 当前位置 -->
        <div class="editPage" style="height:800px;">
            <form id="form" action="${ctx}/admin/member/update" method="post">
                <input type="hidden" name="TOKEN" value="${TOKEN}">
                <input type="hidden" name="id" value="${member.id}">
                <table class="table-module01" cellpadding="0" cellspacing="0" id="tbl">
                    <tr>
                        <th width="130" align="right">会员编号：<b class="color-red">*</b></th>
                        <td>
                            <input type="text" name="code" value="${member.memberCode}"
                                   maxlength="20" disabled/>

                        </td>
                    </tr>
                    <tr>
                        <th width="130" align="right">用户名：<b class="color-red">*</b></th>
                        <td>
                            <input type="text" name="memberName" value="${member.memberName}"
                                   maxlength="20"/>

                        </td>
                    </tr>
                    <tr>
                        <th width="130" align="right">密码：</th>
                        <td>
                            <input type="text" name="password" id="password"
                                   maxlength="64"/>
                        </td>
                    </tr>

                    <tr>
                        <th width="130" align="right">确认密码：</th>
                        <td>
                            <input type="text" name="password2"
                                   maxlength="64"/>
                        </td>
                    </tr>
                    <tr>
                        <th width="130" align="right">邮箱：</th>
                        <td>
                            <input type="text" name="email" value="${member.email}"
                                   maxlength="30"/>

                        </td>
                    </tr>
                    <tr>
                        <th width="130" align="right">手机：<b class="color-red">*</b></th>
                        <td>
                            <input type="text" name="phone" value="${member.phone}"
                                   maxlength="11"/>

                        </td>
                    </tr>
                    <tr>
                        <th width="130" align="right">状态：<b class="color-red">*</b></th>
                        <td>

                            <select id="status" name="status" data-placeholder="请选择" class="select chosen-select ni">
                                <option value="">请选择</option>
                                <c:forEach items="${memberStatus}" var="status">
                                    <option value="${status.dicCode}"
                                            <c:if test="${status.dicCode eq member.status}">selected</c:if> >${status.dicName}</option>
                                </c:forEach>
                            </select>
                            <label for="status" generated="true" class="error"></label>
                        </td>
                    </tr>
                    <tr>
                        <th width="130" align="right">真实姓名：</th>
                        <td>
                            <input type="text" name="realName" value="${member.realName}"
                                   maxlength="20"/>

                        </td>
                    </tr>

                    <tr>
                        <th width="130" align="right">性别：</th>
                        <td>
                            <input type="radio" name="sex" value="1"
                                   <c:if test="${member.sex == 1}">checked</c:if> > 男
                            <input type="radio" name="sex" value="0"
                                   <c:if test="${member.sex == 0}">checked</c:if> > 女
                        </td>
                    </tr>
                    <tr>
                        <th width="130" align="right">会员等级：<%--<b class="color-red">*</b>--%></th>
                        <td>

                            <select id="grade" name="gradeId" data-placeholder="请选择" class="select chosen-select ni">
                                <option value="0">请选择</option>
                                <c:forEach items="${gradeList}" var="grade">
                                    <option value="${grade.id}"
                                            <c:if test="${grade.id eq member.gradeId}">selected</c:if> >${grade.name}</option>
                                </c:forEach>
                            </select>
                            <label for="grade" generated="true" class="error"></label>
                        </td>
                    </tr>
                    <tr>
                        <th width="130" align="right">账户余额：<b class="color-red">*</b></th>
                        <td>
                            <input type="text" value="${member.balance}" name="balance">
                        </td>
                    </tr>
                    <tr>
                        <th width="130" align="right">注册平台：<b class="color-red">*</b></th>
                        <td>
                            <c:forEach items="${froms}" var="from">
                                <c:if test="${member.registerFrom == from.dicCode}">
                                    <input type="text" value="${from.dicName}" disabled>
                                </c:if>
                            </c:forEach>
                        </td>
                    </tr>

                    <tr>
                        <td></td>
                        <td align="left">
	                            <span class="btn-sure-wrap">
	                                <input class="btn-sure btn-edit" type="submit" value="保存"/>
	                            </span>
	                            <span class="btn-cancel-wrap">
	                                <input class="btn-cancel" type="button" value="取消" onclick=""/>
	                            </span>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <!-- //End 主内容区 -->
    </div>
    <m:f/>
    <-- 图片上传校验 -->
    <script type="text/javascript" src="${ctx}/js/admin/imgValidate.js"></script>
    <script type="text/javascript" src="${ctx}/js/admin/member/edit.js"></script>



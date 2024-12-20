<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%
    String active = (String)request.getAttribute("active");
%>

<nav class="sidebar sidebar-offcanvas" id="sidebar">
        <ul class="nav">
        <%-- Home --%>
        <%
          if(active.equals("home")){
        %>
          <li class="nav-item active " style="background-color:#232743;">
            <a class="nav-link" href="/PreHomeEmploye">
              <i class="icon-grid menu-icon"></i>
              <span class="menu-title"> Home </span>
            </a>
          </li>
        <%
          }else{
        %>
          <li class="nav-item ">
            <a class="nav-link" href="/PreHomeEmploye">
              <i class="icon-grid menu-icon"></i>
              <span class="menu-title"> Home </span>
            </a>
          </li>
        <%
          }
        %>

          <%-- Congé --%>
          <%
          if(active.equals("conge")){
        %>
          <li class="nav-item active ">
            <a class="nav-link" href="/PreCongeEmploye">
              <i class="icon-grid menu-icon"></i>
              <span class="menu-title"> Congé </span>
            </a>
          </li>
        <%
          }else{
        %>
          <li class="nav-item ">
            <a class="nav-link" href="/PreCongeEmploye">
              <i class="icon-grid menu-icon"></i>
              <span class="menu-title"> Congé </span>
            </a>
          </li>
        <%
          }
        %>

          <%
          if(active.equals("information")){
          %>
            <%-- Information --%>
            <li class="nav-item active">
              <a class="nav-link" href="/PreInformationEmploye">
                <i class="bi bi-calendar2 menu-icon"></i> <!-- Icône de calendrier -->
                <span class="menu-title">Information</span>
              </a>
            </li>
          <%
          }else {
          %>
            <%-- Information --%>
            <li class="nav-item">
              <a class="nav-link" href="/PreInformationEmploye">
                <i class="bi bi-calendar2 menu-icon"></i> <!-- Icône de calendrier -->
                <span class="menu-title">Information</span>
              </a>
            </li>
          <%
          }
          %>



        </ul>
      </nav>
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
            <a class="nav-link" href="/PreHomeRh">
              <i class="icon-grid menu-icon"></i>
              <span class="menu-title"> Home </span>
            </a>
          </li>
        <%
          }else{
        %>
          <li class="nav-item ">
            <a class="nav-link" href="/PreHomeRh">
              <i class="icon-grid menu-icon"></i>
              <span class="menu-title"> Home </span>
            </a>
          </li>
        <%
          }
        %>

          <%-- Employées --%>
          <li class="nav-item">
            <a class="nav-link" data-toggle="collapse" href="#ui-basic" aria-expanded="false" aria-controls="ui-basic">
              <i class="bi bi-people menu-icon"></i> <!-- Icône de groupe -->
              <span class="menu-title"> Employées </span>
              <i class="menu-arrow"></i>
            </a>
            <div class="collapse" id="ui-basic">
              <ul class="nav flex-column sub-menu">
                <%
                if(active.equals("conge")){
                %>
                  <li class="nav-item active"> <a class="nav-link" href="/PreEmploye"> Liste des employées </a></li>
                <%
                }else{
                %>
                  <li class="nav-item"> <a class="nav-link" href="/PreEmploye"> Liste des employées </a></li>
                <%
                }
                %>
                <li class="nav-item"> <a class="nav-link" href="pages/ui-features/dropdowns.html"> Liste des candidats </a></li>
              </ul>
            </div>
          </li>

          <%
          if(active.equals("conge")){
          %>
            <%-- Congés --%>
            <li class="nav-item active">
              <a class="nav-link" href="/PreConge">
                <i class="bi bi-calendar2 menu-icon"></i> <!-- Icône de calendrier -->
                <span class="menu-title">Congés</span>
              </a>
            </li>
          <%
          }else {
          %>
            <%-- Congés --%>
            <li class="nav-item">
              <a class="nav-link" href="/PreConge">
                <i class="bi bi-calendar2 menu-icon"></i> <!-- Icône de calendrier -->
                <span class="menu-title">Congés</span>
              </a>
            </li>
          <%
          }
          %>

          <%-- Fiche de paie --%>
          


        </ul>
      </nav>
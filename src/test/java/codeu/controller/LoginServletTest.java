// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.controller;

import codeu.model.data.User;
import codeu.model.store.basic.UserStore;

import java.io.IOException;
import java.util.UUID;
import java.time.Instant;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mindrot.jbcrypt.*;

public class LoginServletTest {

  private LoginServlet loginServlet;
  private RegisterServlet registerServlet;
  private HttpServletRequest mockRequest;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private UserStore userStore;
  private User user;
  private static final String USERNAME = "test_username";
  private static final String PASSWORD = "test_password";
  

  @Before
  public void setup() {
    loginServlet = new LoginServlet();
    registerServlet = new RegisterServlet();
    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/login.jsp"))
        .thenReturn(mockRequestDispatcher);
    //TODO make a user and a usertore to be used in this class for test because we need existing users for the existing user do post test
    
  }

  @Test
  public void testDoGet() throws IOException, ServletException {
    loginServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }
  
  @Test
  public void testDoPost_BadPassword() throws IOException, ServletException {

    Mockito.when(mockRequest.getParameter("username")).thenReturn(USERNAME);
    Mockito.when(mockRequest.getParameter("password")).thenReturn(PASSWORD);

    user = Mockito.mock(User.class);
    
    userStore = Mockito.mock(UserStore.class);
    Mockito.when(userStore.isUserRegistered(USERNAME)).thenReturn(true);
        
    loginServlet.setUserStore(userStore);
    
    Mockito.when(userStore.getUser(USERNAME)).thenReturn(user);
    Mockito.when(user.getPassword()).thenReturn(BCrypt.hashpw("bad_password", BCrypt.gensalt()));


    HttpSession mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

    loginServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(userStore, Mockito.never()).addUser(Mockito.any(User.class));

    Mockito.verify(mockRequest).setAttribute("error", "Invalid password.");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }
 
  @Test
  public void testDoPost_BadUser() throws IOException, ServletException {

    Mockito.when(mockRequest.getParameter("username")).thenReturn(USERNAME);
    Mockito.when(mockRequest.getParameter("password")).thenReturn(PASSWORD);

    user = Mockito.mock(User.class);
    
    userStore = Mockito.mock(UserStore.class);
    Mockito.when(userStore.isUserRegistered(USERNAME)).thenReturn(false);
        
    loginServlet.setUserStore(userStore);
  
    HttpSession mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

    loginServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("error", "That username was not found.");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }
  
  @Test	
  public void testDoPost_ExistingUser() throws IOException, ServletException {	
	
    Mockito.when(mockRequest.getParameter("username")).thenReturn(USERNAME);	
    Mockito.when(mockRequest.getParameter("password")).thenReturn(PASSWORD);	
	
    user = Mockito.mock(User.class);	
    	
    userStore = Mockito.mock(UserStore.class);	
    Mockito.when(userStore.isUserRegistered(USERNAME)).thenReturn(true);	
        	
    loginServlet.setUserStore(userStore);	
    	
    Mockito.when(userStore.getUser(USERNAME)).thenReturn(user);	
    Mockito.when(user.getPassword()).thenReturn(BCrypt.hashpw(PASSWORD, BCrypt.gensalt()));
	
	
    HttpSession mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);
	
    loginServlet.doPost(mockRequest, mockResponse);	
	
    Mockito.verify(userStore, Mockito.never()).addUser(Mockito.any(User.class));	
	
    Mockito.verify(mockSession).setAttribute("user", USERNAME);	
    Mockito.verify(mockResponse).sendRedirect("/conversations");	
  }
}

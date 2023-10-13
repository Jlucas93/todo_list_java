package br.com.lucas.todo_list.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.lucas.todo_list.user.IUserRepository;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

  @Autowired
  private IUserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, java.io.IOException {

    var request_path = request.getServletPath();

    if (request_path.equals("/tasks/")) {
      var auth_encoded = request.getHeader("Authorization").substring("Basic".length()).trim();

      byte[] auth_decoded = Base64.getDecoder().decode(auth_encoded);

      var user_string = new String(auth_decoded).split(":");

      var user_name = user_string[0];
      var user_password = user_string[1];

      var user = this.userRepository.findByUserName(user_name);

      if (user == null) {
        response.sendError(401, "Unauthorized");
        return;
      } else {
        var password = BCrypt.verifyer().verify(user_password.toCharArray(), user.getPassword());
        if (password.verified) {
          request.setAttribute("UserId", user.getId());

          filterChain.doFilter(request, response);
          return;
        } else {
          response.sendError(401, "Unauthorized");
          return;
        }
      }
    }
    filterChain.doFilter(request, response);
  }
}
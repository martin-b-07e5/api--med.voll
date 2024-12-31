package med.voll.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @GetMapping("/hello")
  public String hello() {
    return "Hello World!";
  }

  /// ### **@RequestMapping**
  /// - It is more generic and can handle multiple HTTP methods (GET, POST, PUT, DELETE, etc.), depending on how it is configured.
  /// - You must use the `method` attribute to specify the HTTP method, which can make the code longer or less readable if you only need to handle GET requests.
  ///
  /// **Example equivalent to `@GetMapping`:**
  ///
  /// @RequestMapping(value = "/hello", method = RequestMethod.GET)
  /// public String hello() {
  ///     return "Hello World!";
  /// }
  ///
  /// This method will also respond only to **GET** requests at the URL `/hello`.

}

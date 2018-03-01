package spring.mvc.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import spring.mvc.demo.Helpers.EmailToken;
import spring.mvc.demo.Helpers.EmailSender;
import spring.mvc.demo.Models.ChangePassViewModel;
import spring.mvc.demo.Models.TokenModel;
import spring.mvc.demo.Models.UserViewModel;
import spring.mvc.demo.Repository.TokenRepository;
import spring.mvc.demo.Repository.UserRepository;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Controller
@RequestMapping("/Register")
@PropertySource("classpath:appSettings.properties")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;
    private final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Value("${ForgotPassword}")
    private int getForgotPassword;

    @Value("${Activation}")
    private int getActivation;

    @Value("${CryptoSplit}")
    private String getCryptoSplit;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String Register(Model model) {
        model.addAttribute("registerModel", new UserViewModel());
        return View("Register");
    }

    @RequestMapping(value = "/ReActivation", method = RequestMethod.GET)
    public String ReVerification() {
        return View("ReSendActivation");
    }

    @RequestMapping(value = "/ForgotPassword", method = RequestMethod.GET)
    public String ForgotPassword() {
        return View("ForgotPassword");
    }

    @RequestMapping(value = "/ChangeForgotPassword", method = RequestMethod.GET)
    public String Register(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return View("ChangeForgotPassword");
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String Register(@ModelAttribute("registerModel") UserViewModel userViewModel, Model model) throws SQLException, ClassNotFoundException {
        if (userViewModel.IsValid()) {
            if (userRepository.GetUser(userViewModel.getEmail()) != null) {
                model.addAttribute("msg", "Database has " + userViewModel.getEmail() + ",please try different Email");
                model.addAttribute("alert", "alert-warning");
                model.addAttribute("registerModel", model);
                return View("Register");
            }
            try {
                userViewModel.setEnable(false);
                userViewModel.setConfirmPassword(null);
                userRepository.save(userViewModel);

                TokenModel tokenModel = new TokenModel();
                tokenModel.setEmail(userViewModel.getEmail());
                tokenModel.setToken(new EmailToken().CreateToken());
                tokenModel.setType(getActivation);
                tokenRepository.save(tokenModel);

                EmailSender emailSender = new EmailSender();
                emailSender.ActivationSender(new EmailToken().CreateMailToken(userViewModel.getEmail(), tokenModel.getToken()), tokenModel.getEmail());

                model.addAttribute("msg", "Registration is successfully created.");
                model.addAttribute("alert", "alert-success");
                return View("Register");
            } catch (Exception e) {
                model.addAttribute("msg", "An error occured during registration." + e.getMessage());
                model.addAttribute("alert", "alert-danger");
                return View("Register");
            }

        } else {
            model.addAttribute("msg", "Incorrect Fields!");
            model.addAttribute("alert", "alert-warning");
            model.addAttribute("registerModel", model);
            return View("Register");
        }

    }

    @RequestMapping(value = "/ForgotPassword/Post", method = RequestMethod.GET)
    public String ForgotPassword(@RequestParam("email") String email, Model model) throws Exception {
        email = java.net.URLDecoder.decode(email, "UTF-8");
        UserViewModel user = userRepository.GetUser(email);
        if (user != null) {
            EmailToken emailToken = new EmailToken();
            String token = emailToken.CreateToken();
            TokenModel tokenModel = tokenRepository.GetToken(email, getForgotPassword);
            if (tokenModel == null) {
                tokenModel = new TokenModel();
                tokenModel.setEmail(email);
            }

            tokenModel.setToken(token);
            tokenModel.setType(getForgotPassword);
            tokenRepository.save(tokenModel);
            if (new EmailSender().PasswordSender(emailToken.CreateMailToken(email, token), email)) {
                model.addAttribute("msg", "Forgot Password mail is sended");
                model.addAttribute("alert", "alert-success");
                return View("Activation");
            } else {
                model.addAttribute("msg", "An error occured while activation mail sending.");
                model.addAttribute("alert", "alert-warning");
                return View("Activation");
            }
        } else {
            model.addAttribute("msg", "Email did not match any user.");
            model.addAttribute("alert", "alert-info");
            return View("Activation");
        }
    }

    @RequestMapping(value = "/ChangeForgotPassword", method = RequestMethod.POST)
    public String ForgotPassword(Model model, @ModelAttribute("changePassModel") ChangePassViewModel passViewModel) throws Exception {
        String[] splitToken = new EmailToken().decrypt(passViewModel.getToken()).split(getCryptoSplit);
        TokenModel tokenModel = tokenRepository.GetToken(splitToken[0], getForgotPassword);
        if (tokenModel == null) {
            model.addAttribute("msg", "Token is wrong");
            model.addAttribute("alert", "alert-success");
            return View("Activation");
        }
        if (splitToken[1].equals(tokenModel.getToken())) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(splitToken[2]));
            calendar.add(Calendar.DATE, 1);
            if (calendar.getTime().after(Calendar.getInstance().getTime())) {
                UserViewModel user = userRepository.GetUser(tokenModel.getEmail());
                if (passViewModel.equals(passViewModel.getConfirmPassword())) {
                    model.addAttribute("msg", "Passwords did not match.");
                    model.addAttribute("alert", "alert-danger");
                    return View("Activation");
                }
                user.setPassword(passViewModel.getPassword());
                userRepository.save(user);
                tokenRepository.delete(tokenModel);
                model.addAttribute("msg", "Password is Changed.");
                model.addAttribute("alert", "alert-success");
                return View("Activation");
            } else {
                model.addAttribute("msg", "Activation time is expired.");
                model.addAttribute("alert", "alert-warning");
                return View("Activation");
            }

        }
        model.addAttribute("msg", "Token is wrong");
        model.addAttribute("alert", "alert-success");
        return View("Activation");
    }

    @RequestMapping(value = "/Activation", method = RequestMethod.GET)
    public String Verification(@RequestParam("token") String token, Model model) throws Exception {
        String[] splitToken = new EmailToken().decrypt(token).split(getCryptoSplit);
        TokenModel tokenModel = tokenRepository.GetToken(splitToken[0], getActivation);
        if (tokenModel == null) {
            model.addAttribute("msg", "Token is wrong1" + getCryptoSplit);
            model.addAttribute("alert", "alert-danger");
            return View("Activation");
        }
        if (splitToken[1].equals(tokenModel.getToken())) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(splitToken[2]));
            calendar.add(Calendar.DATE, 1);
            if (calendar.getTime().after(Calendar.getInstance().getTime())) {

                tokenRepository.delete(tokenModel);

                UserViewModel user = userRepository.GetUser(tokenModel.getEmail());
                user.setEnable(true);
                userRepository.save(user);

                model.addAttribute("msg", "Activation is successfully.");
                model.addAttribute("alert", "alert-success");
                return View("Activation");
            } else {
                model.addAttribute("msg", "Activation time is expired.");
                model.addAttribute("alert", "alert-warning");
                return View("Activation");
            }

        }
        model.addAttribute("msg", "Token is wrong");
        model.addAttribute("alert", "alert-darger");
        return View("Activation");
    }

    @RequestMapping(value = "/ReSendActivation", method = RequestMethod.GET)
    public String ReVerification(@RequestParam("email") String email, Model model) throws Exception {
        UserViewModel user = userRepository.GetUser(email);
        if (user != null) {
            if (user.isEnable()) {
                model.addAttribute("msg", "Account is enable");
                model.addAttribute("alert", "alert-success");
                return View("Activation");
            }
            EmailToken emailToken = new EmailToken();
            String token = emailToken.CreateToken();
            TokenModel tokenModel = tokenRepository.GetToken(email, getActivation);
            if (tokenModel == null) {
                tokenModel = new TokenModel();
                tokenModel.setEmail(email);
            }
            tokenModel.setToken(token);
            tokenModel.setType(getActivation);
            tokenRepository.save(tokenModel);
            if (new EmailSender().ActivationSender(emailToken.CreateMailToken(email, token), email)) {
                model.addAttribute("msg", "Activation mail is sended");
                model.addAttribute("alert", "alert-success");
                return View("Activation");
            } else {
                model.addAttribute("msg", "An error occured while activation mail sending.");
                model.addAttribute("alert", "alert-warning");
                return View("Activation");
            }
        } else {
            model.addAttribute("msg", "Email did not match any user.");
            model.addAttribute("alert", "alert-info");
            return View("Activation");
        }


    }

    private String View(String s) {
        return "/User/" + s;
    }
}

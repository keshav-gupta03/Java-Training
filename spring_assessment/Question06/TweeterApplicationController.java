package assessment.spring_assessment.Question06;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;


@Controller
public class TweeterApplicationController {
    private Map<String, User> userProfile = new HashMap<>();
    private Map<String, List<Tweet>> tweets = new HashMap<>();
    private Map<String, List<String>> following = new HashMap<>();
    @Autowired
    private UserDao userDao;
    @Autowired
    private TweetDao tweetDao;

    public TweeterApplicationController() {
//        assert userDao != null;
//        List<User> list = userDao.readAll();
//        for (User user : list) {
//            userProfile.put(user.getEmail(), user);
//        }

    }

    /*
    Template Engine -> Mustache, JSP, Thymeleaf
     */
    @GetMapping("/displayUserDetails")
    public ModelAndView getUserDetails() {
        ModelAndView modelAndView = new ModelAndView("users");
        if (userProfile.isEmpty())
            allAccDetails();
        List<User> users = new ArrayList<>();
        for (Map.Entry entry : userProfile.entrySet()) {
            users.add((User) entry.getValue());
        }
        modelAndView.getModel().put("users", users);
        return modelAndView;
    }

    @GetMapping("/loginForm")
    public ModelAndView loginForm() {
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }

    @GetMapping("/getTweets")
    public ModelAndView fetchTweets(@RequestParam String email) {
        List<Tweet> tweetList = userDao.getTweetByEmail(email);
        ModelAndView modelAndView = new ModelAndView("tweets");
        modelAndView.getModel().put("tweets",tweetList);
        modelAndView.getModel().put("name",tweetList.get(0).getName());

        return modelAndView;

    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView login(@RequestBody MultiValueMap<String, String> formData) {
        if (!isUserValid(formData)) {
            return errorMessageModelAndView("Wrong credentials");
        }
        ModelAndView modelAndView = new ModelAndView("profile");
        String email = formData.get("email").get(0);
        String name = userProfile.get(email).getName();
        modelAndView.getModel().put("name", name);
        modelAndView.getModel().put("email", email);
        return modelAndView;
    }


    private ModelAndView errorMessageModelAndView(String message) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.getModel().put("message", message);
        return modelAndView;
    }

    private boolean isUserValid(MultiValueMap<String, String> map) {
        String email = map.get("email").get(0);
        String password = map.get("password").get(0);
        User user = userProfile.get(email);
        if (user.getPassword().equals(password))
            return true;
        return false;
    }


    //    User can create an account  -->POST
    @PostMapping("/create")
    @ResponseBody
    private ResponseEntity<String> createUser(@RequestBody User user) {
        ResponseEntity<String> responseEntity = null;
        boolean bool=true;
        List<User> userList=userDao.readAll();
        for(int i=0;i<userList.size();i++){
            User user1=userList.get(i);
            if(user1.getEmail().equals(user.getEmail())){
                responseEntity = new ResponseEntity<>("User already registered!",
                        HttpStatus.BAD_REQUEST);
                bool=false;
               break;
            }
        }
        if (!bool) {
            responseEntity = new ResponseEntity<>("User already registered!",
                    HttpStatus.BAD_REQUEST);
        } else {
            String email = user.getEmail();
            userDao.create(user);
            userProfile.put(email, user);
            responseEntity = new ResponseEntity<>("User account created successfully!", HttpStatus.OK);
        }
        return responseEntity;
    }

    //    User can all account details --> GET
    @GetMapping("/fetchUsers")
    @ResponseBody
    Map<String, User> allAccDetails() {
        Map<String, User> ans=new HashMap<>();
        List<User> users=userDao.readAll();
        for(int i=0;i< users.size();i++){
            User user=users.get(i);
            String email=user.getEmail();
            ans.put(email,user);
        }
        return ans;
    }

    //    User can fetch particular account details --> GET
    @RequestMapping("/getDetails")
    @GetMapping("/getDetails")
    @ResponseBody
    private ResponseEntity<User> getAccDetails(@RequestParam("email") String email, @RequestParam("password") String password) {
        ResponseEntity<User> responseEntity = null;
        List<User> userList=userDao.readAll();
        for(int i=0;i<userList.size();i++){
            User user=userList.get(i);
            String email1=user.getEmail();
            if(email.equals(email1)){
                if(user.getPassword().equals(password)){
                    responseEntity=new ResponseEntity<>(user,HttpStatus.OK);
                    return responseEntity;
                }
                else {
                    System.out.println("Wrong Password");
                    responseEntity = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                    return responseEntity;
                }
            }
            else {
                System.out.println("User is not registered");
                responseEntity = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                return responseEntity;
            }
        }
        return responseEntity;
    }

    //    User can update account details  -->PUT
    @PutMapping("/update")
    @ResponseBody
    private ResponseEntity<String> updateRecord(@RequestBody User user) {
        String email = user.getEmail();
        String updatedName = user.getName();
        String password = user.getPassword();
        ResponseEntity<String> responseEntity = null;
        if (password.equals(userProfile.get(email).getPassword())) {
            if (containsInvalidChars(updatedName)) {
                responseEntity = new ResponseEntity<>("name contains invalid characters",
                        HttpStatus.BAD_REQUEST);
            } else if (userProfile.containsKey(email)) {
                String currName = userProfile.get(email).getName();
                if (currName.equals(updatedName)) {
                    responseEntity = new ResponseEntity<>("No change rquired",
                            HttpStatus.OK);
                } else {
                    userProfile.get(email).setName(updatedName);
                    //write update query here
                    responseEntity = new ResponseEntity<>("update successful",
                            HttpStatus.OK);
                }
            } else {
                responseEntity = new ResponseEntity<>("User doesn't exist",
                        HttpStatus.NOT_FOUND);
            }
        } else {
            responseEntity = new ResponseEntity<>("Wrong password",
                    HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    private boolean containsInvalidChars(String name) {
        // name contains any numbers or +-
        return false;
    }


    //    User can delete account  -->DELETE
    @DeleteMapping("/delete")
    @ResponseBody
    private ResponseEntity<String> deleteRecord(@RequestParam String email, String password) {
        ResponseEntity<String> responseEntity = null;
        List<User> userList=userDao.readAll();
        for(int i=0;i<userList.size();i++){
            User user=userList.get(i);
            String email1=user.getEmail();
            String password1=user.getPassword();
            if(email.equals(email1)){
                if(password1.equals(password)){
                    userDao.delete(user);
                    responseEntity =new ResponseEntity<>("User Deleted Successfully",HttpStatus.OK);
                    return responseEntity;
                }
                else {
                    responseEntity = new ResponseEntity<>("Wrong Password", HttpStatus.BAD_REQUEST);
                    return responseEntity;
                }
            }
            else {
                responseEntity = new ResponseEntity<>("User is not registered", HttpStatus.BAD_REQUEST);

            }
        }
        return responseEntity;
    }

    //User can create tweet -->POST
    @PostMapping("/createTweet")
    @ResponseBody
    ResponseEntity<String> createTweet(@RequestBody Tweet tweet, @RequestParam String password) {
        ResponseEntity<String> responseEntity = null;
        List<User> users = userDao.readAll();
        for(int i=0;i<users.size();i++){
            User user=users.get(i);
            if(user.getEmail().equals(tweet.getEmail())){
                if(user.getPassword().equals(password)){
                    tweetDao.createTweet(tweet);
                    responseEntity = new ResponseEntity<>("Tweet added successfully", HttpStatus.OK);
                    return responseEntity;
                }
                else {
                    responseEntity = new ResponseEntity<>("Wrong Password", HttpStatus.BAD_REQUEST);
                    return responseEntity;
                }
            }
        }
        responseEntity=new ResponseEntity<>("User is not register",HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    //user can see all tweets
    @GetMapping("/fetchTweets")
    @ResponseBody
    Map<String,List<Tweet>> fetchTweets() {
        List<Tweet> list = userDao.getAllTweets();
        Map<String,List<Tweet>> allTweets=new HashMap<>();
        for(int i=0;i< list.size();i++){
            Tweet tweet=list.get(i);
            String email=tweet.getEmail();
            if(allTweets.containsKey(email)){
                allTweets.get(email).add(tweet);
            }
            else{
                List<Tweet> tweetList = new ArrayList<>();
                tweetList.add(tweet);
                allTweets.put(email,tweetList);
            }

        }
        return allTweets;
    }

    //user can see tweets from a particular account
    @GetMapping("/fetchTweetsOfUser")
    @ResponseBody
    List<Tweet> fetchTweetsOfUser(@RequestParam String email) {
        List<Tweet> list = userDao.getAllTweets();
        List<Tweet> tweetList = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Tweet tweet=list.get(i);
            if(tweet.getEmail().equals(email)){
                tweetList.add(tweet);
            }
        }
        return tweetList;
    }

    //user can follow another user
    @PostMapping("/followUser")
    @ResponseBody
    private ResponseEntity<String> followUsers(@RequestParam String email, String userEmail) {
        ResponseEntity<String> responseEntity = null;
        if (!userProfile.containsKey(email)) {
            responseEntity = new ResponseEntity<>("User doesn't exist", HttpStatus.BAD_REQUEST);
            return responseEntity;
        }
        if (userEmail.equals(email)) {
            responseEntity = new ResponseEntity<>("You can't follow yourself", HttpStatus.BAD_REQUEST);
            return responseEntity;
        }
        if (userProfile.get(userEmail) != null) {
            if (following.containsKey(email)) {
                following.get(email).add(userEmail);
            } else {
                List<String> list = new ArrayList<>();
                list.add(userEmail);
                following.put(email, list);
            }
            responseEntity = new ResponseEntity<>("User " + userEmail + " followed successfully"
                    , HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>("User you are following doesn't exist"
                    , HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    //to view following of a user
    @GetMapping("/follow")
    @ResponseBody
    private List<String> following(@RequestParam String email) {
        if (following.containsKey(email))
            return following.get(email);
        else
            return Arrays.asList("User doesn't follow anyone");
    }

}
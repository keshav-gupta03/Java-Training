package assessment.spring_assessment.Question2;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FacebookAPIController {
    @Autowired
    Dao<User> UserDao;

    @Autowired
    Dao<Post> PostDao;

    private Map<String, User> UserProfileMap = new HashMap<>();
    private Map<String, List<Post>> postmap = new HashMap<>();
    private Map<String, List<User>> followmap = new HashMap<>();

    //using of that we can open login page
    @GetMapping(value="fb/user/login")
    public ModelAndView openLogin(){
        ModelAndView modelAndView = new ModelAndView("fb/fblogin");
        return modelAndView;
    }

    //using of that we can open registration page
    @GetMapping(value="fb/user/registration")
    public ModelAndView openRegister(){
        ModelAndView modelAndView = new ModelAndView("fb/fbregistration");
        return modelAndView;
    }


    //     show all users on users mustache file -->GET
    @GetMapping("fb/user/displaymypost")
    public ModelAndView getUsermypost(@RequestParam String email){
        ModelAndView modelAndView=new ModelAndView("fb/mypost");
        List<Post> postList = getFbPostbyuserEmail(email);
        if (postList.isEmpty() || !postmap.containsKey(email))
            return errorMessageModelAndView("Wrong credentials");
        modelAndView.getModel().put("posts",postList);
        return modelAndView;
    }

    @PostMapping(value="fb/user/loginCheck", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView checkLogin(@RequestBody MultiValueMap<String, String> formData){
        ModelAndView modelAndView=null;
        if (!isUserValid(formData) || formData.isEmpty()) {
            System.out.println("Wrong credential");
            return errorMessageModelAndView("Wrong credentials");
        }
        modelAndView = new ModelAndView("fb/fbprofile");
        String email = formData.get("email").get(0);
        String name = UserProfileMap.get(email).getName();
        modelAndView.getModel().put("name", name);
        modelAndView.getModel().put("email", email);
        modelAndView.getModel().put("password", UserProfileMap.get(email).getPassword());
        modelAndView.getModel().put("post",getFbPostbyuserEmail(email));
        return modelAndView;
    }

    private boolean isUserValid(MultiValueMap<String, String> map) {
        getAllFbUsers();
        String email = map.get("email").get(0);
        String password = map.get("password").get(0);
        User user = UserProfileMap.get(email);
        if (user.getPassword().equals(password))
            return true;
        return false;
    }

    @PostMapping( value = "fb/user/createuser", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView getRegiseteredData(@RequestBody MultiValueMap<String, String> formData){
        if (formData.isEmpty()){
            return errorMessageModelAndView(new ResponseEntity<>("No such data",HttpStatus.BAD_REQUEST).toString());
        }
        String name=formData.get("name").get(0);
        String email=formData.get("email").get(0);
        String password=formData.get("password").get(0);
        return errorMessageModelAndView(createUser(new User(name,email,password)).toString());
    }

    private ModelAndView errorMessageModelAndView(String message) {
        ModelAndView modelAndView = new ModelAndView("fb/error");
        System.out.println("message: "+message);
        modelAndView.getModel().put("message", message);
        return modelAndView;
    }

    /*----------------users API----------------------------------------*/

    /**
     * @USE using that fatch all the fb user
     * @return user map
     * @METHOD GET
     */
    @GetMapping("/fb/user/fecthAllUser")
    public  Map<String ,User> getAllFbUsers(){
        UserProfileMap.clear();
        List<User> list = UserDao.readAll();
        for (User user:list)
            UserProfileMap.put(user.getEmail(),user);
        System.out.println("allpost:"+ UserProfileMap);
        return UserProfileMap;
    }

    /**
     * @USE using that fatch the fb user by id
     * @return user map
     * @METHOD GET
     */
    @GetMapping("/fb/user/fecthUserdetail")
    public User getFbUserById(@RequestParam int id){
        return UserDao.readById(id);
    }

    /**
     * @USE using that fatch the fb user by id
     * @return user map
     * @METHOD GET
     */
    @GetMapping("/fb/user/fecthUserDetailByEmail")
    public int getFbUserByEmail(@RequestParam String email){
        getAllFbUsers();
        return (int) UserProfileMap.get(email).getId();
    }

    /**
     * @USE using that we can create user web and postman both
     * @param user
     * @return responseEntity
     * @METHOD POST
     */
    @PostMapping("/user/createUser")
    private ResponseEntity<String> createUser(@RequestBody User user){
        getAllFbUsers();
        ResponseEntity<String> responseEntity=null;
        String name=user.getName();
        String email=user.getEmail();
        String password=user.getPassword();
        if (containsInvalidChar(name))
            responseEntity=new ResponseEntity<>("name contains invalid character", HttpStatus.BAD_REQUEST);
        else if (containsInvalidEmail(email) || isContainsEmail(email)){
            responseEntity=new ResponseEntity<>("Email contains invalid character or alerady present",HttpStatus.BAD_REQUEST);
        }else if(containsInvalidPassword(password)){
            responseEntity=new ResponseEntity<>("Password contains invalid",HttpStatus.BAD_REQUEST);
        }else {
            UserDao.create(user);
            UserProfileMap.put(email,user);
            responseEntity=new ResponseEntity<>("Registered successfully",HttpStatus.OK);
        }
        return responseEntity;
    }

    /**
     * @USE using that we can update user web and postman both
     * @param user
     * @return responseEntity
     * @METHOD POST
     */
    @PostMapping("fb/user/updateUser")
    private ResponseEntity<String> updateUser(@RequestBody User user){
        getAllFbUsers();
        ResponseEntity<String> responseEntity=null;
        String name=user.getName();
        String email=user.getEmail();
        String password=user.getPassword();
        if (containsInvalidChar(name))
            responseEntity=new ResponseEntity<>("name contains invalid character", HttpStatus.BAD_REQUEST);
        else if (containsInvalidEmail(email) || !isContainsEmail(email)){
            responseEntity=new ResponseEntity<>("Email contains invalid character or alerady present",HttpStatus.BAD_REQUEST);
        }else if(containsInvalidPassword(password)){
            responseEntity=new ResponseEntity<>("Password contains invalid",HttpStatus.BAD_REQUEST);
        }else {
            if(UserDao.update(user)) {
                UserProfileMap.put(email, user);
                responseEntity = new ResponseEntity<>("Update successfully", HttpStatus.OK);
            }else
                responseEntity = new ResponseEntity<>("Something wrong", HttpStatus.NOT_ACCEPTABLE);
        }
        return responseEntity;
    }

    /**
     * @USE using that we can update user web and postman both
     * @param user
     * @return responseEntity
     * @METHOD POST
     */
    @PostMapping("fb/user/deleteUser")
    private ResponseEntity<String> deleteUser(@RequestBody User user){
        getAllFbUsers();
        ResponseEntity<String> responseEntity=null;
        String name=user.getName();
        String email=user.getEmail();
        String password=user.getPassword();
        user = UserProfileMap.get(email);
        if (containsInvalidChar(name))
            responseEntity=new ResponseEntity<>("name contains invalid character", HttpStatus.BAD_REQUEST);
        else if (containsInvalidEmail(email) || !isContainsEmail(email)){
            responseEntity=new ResponseEntity<>("Email contains invalid character or not present",HttpStatus.BAD_REQUEST);
        }else if(containsInvalidPassword(password)){
            responseEntity=new ResponseEntity<>("Password contains invalid",HttpStatus.BAD_REQUEST);
        }else {
            UserDao.delete(user);
            UserProfileMap.remove(email, user);
            responseEntity = new ResponseEntity<>("Delete successfully", HttpStatus.OK);
//            responseEntity = new ResponseEntity<>("Something wrong", HttpStatus.NOT_ACCEPTABLE);
        }
        return responseEntity;
    }


    /*-------------------------post API-------------------------------*/

    /**
     * @USE using that fatch all the fb user
     * @return user map
     * @METHOD GET
     */
    @GetMapping("/fb/post/fecthAllPost")
    public Map<String ,List<Post>> getAllFbPost(){
        postmap.clear();
        List<Post> list = PostDao.readAll();
        for (Post post:list){
            String email=post.getUser().getEmail();
            if(postmap.containsKey(email)){
                postmap.get(email).add(post);
            }
            else {
                List<Post> postList =new ArrayList<>();
                postList.add(post);
                postmap.put(email,postList);
            }
        }
        return postmap;
    }

    /**
     * @USE using that fatch all the fb user
     * @return user map
     * @METHOD GET
     */
    @GetMapping("/fb/post/fecthPostbyId")
    public Post getFbPostbyuserId(@RequestParam int id){
        return PostDao.readById(id);
    }

    /**
     * @USE using that fatch all the fb user by email
     * @return user map
     * @METHOD GET
     */
    @GetMapping("/fb/post/fecthPostbyEmail")
    public List<Post> getFbPostbyuserEmail(@RequestParam String email){
        getAllFbPost();
        return postmap.get(email);
    }


    /**
     * @Use using that we can add new post
     * @param fbpost
     * @param email
     * @param password
     * @return response
     * @METHOD POST
     */
    @PostMapping("/POST/createPost")
    private ResponseEntity<String> createPost(@RequestBody Post fbpost, @RequestParam String email,String password){
        getAllFbUsers();
        ResponseEntity<String> responseEntity=null;
        String post=fbpost.getPost();
        if (containsInvalidChar(post))
            responseEntity=new ResponseEntity<>("Post contains invalid character", HttpStatus.BAD_REQUEST);
        else if (containsInvalidEmail(email) || !isContainsEmail(email)){
            responseEntity=new ResponseEntity<>("Email contains invalid character or not present",HttpStatus.BAD_REQUEST);
        }else if(containsInvalidPassword(password)){
            responseEntity=new ResponseEntity<>("Password contains invalid",HttpStatus.BAD_REQUEST);
        }else if(isPasswordMatch(email, password)) {
            if (postmap.containsKey(email)){
                fbpost.setUser(UserProfileMap.get(email));
                fbpost.setLocalDateTime(LocalDateTime.now());
                PostDao.create(fbpost);
                postmap.get(email).add(fbpost);
            }else{
                fbpost.setUser(UserProfileMap.get(email));
                fbpost.setLocalDateTime(LocalDateTime.now());
                PostDao.create(fbpost);
                List<Post> list=new ArrayList<>();
                list.add(fbpost);
                PostDao.create(fbpost);
                postmap.put(email,list);
                responseEntity=new ResponseEntity<>("Post added successfully",HttpStatus.OK);
            }
        }else
            responseEntity=new ResponseEntity<>("Password incorrect",HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    /**
     * @USE using that we can update user web and postman both
     * @param post
     * @return responseEntity
     * @METHOD POST
     */
    @PostMapping("fb/post/deletePost")
    private ResponseEntity<String> deletePost(@RequestBody Post post){
        getAllFbUsers();
        ResponseEntity<String> responseEntity=null;
        String postt= post.getPost();
        String email=post.getUser().getEmail();
        if (containsInvalidChar(postt))
            responseEntity=new ResponseEntity<>("name contains invalid character", HttpStatus.BAD_REQUEST);
        else if (containsInvalidEmail(email) || !isContainsEmail(email)){
            responseEntity=new ResponseEntity<>("Email contains invalid character or not present",HttpStatus.BAD_REQUEST);
        }else {
            PostDao.delete(post);
            responseEntity = new ResponseEntity<>("Delete successfully", HttpStatus.OK);
        }
        return responseEntity;
    }

    /**
     * @Use using that we can add update post
     * @param fbpost
     * @param email
     * @param password
     * @return response
     * @METHOD POST
     */
    @PostMapping("/post/updatePost")
    private ResponseEntity<String> updatePost(@RequestBody Post fbpost, @RequestParam String email,String password){
        getAllFbUsers();
        ResponseEntity<String> responseEntity=null;
        String post=fbpost.getPost();
        if (containsInvalidChar(post))
            responseEntity=new ResponseEntity<>("Post contains invalid character", HttpStatus.BAD_REQUEST);
        else if (containsInvalidEmail(email) || !isContainsEmail(email)){
            responseEntity=new ResponseEntity<>("Email contains invalid character or not present",HttpStatus.BAD_REQUEST);
        }else if(containsInvalidPassword(password)){
            responseEntity=new ResponseEntity<>("Password contains invalid",HttpStatus.BAD_REQUEST);
        }else if(isPasswordMatch(email, password)) {
            if (postmap.containsKey(email)){
                fbpost.setUser(UserProfileMap.get(email));
                PostDao.create(fbpost);
                postmap.get(email).add(fbpost);
            }else{
                fbpost.setUser(UserProfileMap.get(email));
                PostDao.create(fbpost);
                List<Post> list=new ArrayList<>();
                list.add(fbpost);
                PostDao.create(fbpost);
                postmap.put(email,list);
                responseEntity=new ResponseEntity<>("Post added successfully",HttpStatus.OK);
            }
        }else
            responseEntity=new ResponseEntity<>("Password incorrect",HttpStatus.BAD_REQUEST);
        return responseEntity;
    }

    /*----------------------- follow friend-----------------------------*/

    @PostMapping("/addfriend")
    private ResponseEntity<String> addFriend(@RequestBody MultiValueMap<String ,String> mapdata){
        getAllFbUsers();
        ResponseEntity<String> responseEntity=null;
        String email=mapdata.get("email").get(0);
        String friendEmail=mapdata.get("friendEmail").get(0);
        if (!isContainsEmail(email) || !isContainsEmail(friendEmail))
            responseEntity=new ResponseEntity<>("Email not registered",HttpStatus.NOT_ACCEPTABLE);
        else{
            if (followmap.containsKey(email)){

            }

        }
        return responseEntity;
    }

    /*----------------------validation ------------------------------*/

    boolean containsInvalidPassword(String password) {
        if (password.equals(null))
            return true;
        return false;
    }

    boolean isContainsEmail(String email) {
        if (UserProfileMap.containsKey(email))
            return true;
        return false;
    }

    boolean isPasswordMatch(String email,String password){
        if(UserProfileMap.get(email).getPassword().equals(password))
            return true;
        return false;
    }

    boolean containsInvalidEmail(String email) {
        if (email.equals(null))
            return true;
        return false;
    }

    boolean containsInvalidChar(String name) {
        if (name.equals(null))
            return true;
        return false;
    }

}
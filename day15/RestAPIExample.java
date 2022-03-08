package day15;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.TabExpander;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RestAPIExample {

    private Map<String,String> userlist =new HashMap<>();
    private Map<String,List<Tweet>> tweets = new HashMap<>();

    private boolean containsInvalidChars(String name) {
        if(name.length()<4)
            return false;
        else {
            for(int i=0;i<name.length();i++){
                char c=name.charAt(i);
                if( Character.isDigit(c)){
                    System.out.println(c);
                    return false;
                }
            }
        }
        return true;
    }


    @PostMapping("/tweet")
    public ResponseEntity<String>tweet(@RequestBody Tweet tweet){
        System.out.println(tweet.toString());
        ResponseEntity<String> responseEntity=null;
        String email = tweet.getEmail();
        if(tweets.containsKey(email)) {
//            ArrayList<Tweet> al= new ArrayList<>();
//            al.add(tweet);
//            tweets.put(email,al);
            tweets.get(email).add(tweet);
            responseEntity=new ResponseEntity<>("Tweet uploaded successfully",HttpStatus.OK);
        }else {
            responseEntity=new ResponseEntity<>("User does not exist please create account first ",HttpStatus.OK);
        }
        return responseEntity;
    }

    //GET all the tweets of the user
    @GetMapping("/alltweets")
    public ResponseEntity<List<Tweet>> getTweet(@RequestParam String email){
        ResponseEntity<List<Tweet>> responseEntity=null;
        if(userlist.containsKey(email)){
            responseEntity =new ResponseEntity<List<Tweet>>((List<Tweet>) tweets.get(email),HttpStatus.OK);
        }
        else {
            responseEntity=new ResponseEntity<List<Tweet>>((List<Tweet>) tweets.get(email),HttpStatus.OK);
        }
        return responseEntity;
    }


    //    User can create an account  -->POST
    @PostMapping("/create")
    ResponseEntity<String> getTweets(@RequestBody Map<String,String> requestBodyMap){
        ResponseEntity<String> responseEntity=null;
        String email = requestBodyMap.get("email");
        String name = requestBodyMap.get("name");
        if(!containsInvalidChars(name)){
            responseEntity=new ResponseEntity<>("Name is not valid ",HttpStatus.OK);
            return responseEntity;
        }
        if(userlist.containsKey(email))
            responseEntity=new ResponseEntity<>("User already exist",HttpStatus.OK);
        else {
            userlist.put(email,name);
            List<Tweet> list=new ArrayList<>();
            tweets.put(email,list);
            responseEntity=new ResponseEntity<>("User Successfully Created",HttpStatus.OK);
        }

        return responseEntity;
    }



    //    User can fetch account details --> GET
    @GetMapping("/fetch")
    ResponseEntity<String> getAccDetails(){
        ResponseEntity<String> responseEntity=null;
        if(userlist.size()==0)
            responseEntity=new ResponseEntity<>("NO user found",HttpStatus.NOT_FOUND);
        else
            responseEntity=new ResponseEntity<>(userlist.toString(),HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/getDetails")
    private ResponseEntity<String> getAccDetails(@RequestParam String email) {
        ResponseEntity<String> responseEntity=null;
        if(userlist.containsKey(email))
            responseEntity=new ResponseEntity<>(userlist.get(email),HttpStatus.OK);
        else
            responseEntity=new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND);
        return responseEntity;
    }


    //    User can update account details  -->PUT
    @PutMapping("/update")
    ResponseEntity<String> updateRecord(@RequestBody Map<String, String> requestBodyMap){
        ResponseEntity<String > responseEntity=null;
        String email = requestBodyMap.get("email");
        String name = requestBodyMap.get("name");

         if(userlist.containsKey(email)){
             if (!containsInvalidChars(name)) {
                 responseEntity = new ResponseEntity<>("name contains invalid characters", HttpStatus.BAD_REQUEST);
                 return responseEntity;
             }
            String currName= userlist.get(email);
            if(currName==name){
                responseEntity=new ResponseEntity<>("No change is required",HttpStatus.OK);
                return responseEntity;
            }
            else
            {
                userlist.put(email,name);
                responseEntity=new ResponseEntity<>("Successfully Updated",HttpStatus.OK);
                return responseEntity;
            }

        }
        else {
            responseEntity=new ResponseEntity<>("user not found",HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }


    //    User can delete account  -->DELETE
    @DeleteMapping("/delete")
    ResponseEntity<String > deleteRecord(@RequestBody String email){
        ResponseEntity<String > responseEntity=null;
        if(userlist.containsKey(email)){
            userlist.remove(email);
            responseEntity=new ResponseEntity<>(email+"successfully deleted",HttpStatus.OK);
        }
        else
            responseEntity=new ResponseEntity<>("user not exists",HttpStatus.OK);
        return responseEntity;
    }


}

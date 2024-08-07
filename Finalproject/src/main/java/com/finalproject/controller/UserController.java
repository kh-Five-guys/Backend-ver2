package com.finalproject.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.finalproject.dto.UserDTO;
import com.finalproject.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class UserController {
    private final UserService service;
    
    @Autowired
    private JavaMailSender mailSender;

    public UserController(UserService service) {
        this.service = service;
    }

    // 회원 삭제
    @DeleteMapping("/members/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        int result = service.deleteUser(userId);
        if (result > 0) {
            return ResponseEntity.ok("회원 삭제 완료");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원 삭제 실패");
        }
    }

    // 회원 등급 변경
    @PostMapping("/members/{userId}/rank")
    public ResponseEntity<String> updateUserRank(@PathVariable String userId, @RequestBody Map<String, Integer> rankData) {
        int newRankNo = rankData.get("rankNo");
        int result = service.updateUserRank(userId, newRankNo);
        if (result > 0) {
            return ResponseEntity.ok("회원 등급 변경 완료");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원 등급 변경 실패");
        }
    }
    
    //회원 수정  (수정해야함, 실행은 됌 gpt 사용)
    @PostMapping("/updateProfile")
    public ResponseEntity<String> updateProfile(
            @RequestParam("nickName") String nickName,
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam(value = "newPassword", required = false) String newPassword,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "extraAddress", required = false) String extraAddress,
            @RequestParam(value = "detailAddress", required = false) String detailAddress,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
            @RequestParam(value = "latitude", required = false) Double latitude,
            @RequestParam(value = "longitude", required = false) Double longitude,
            HttpSession session) {

        System.out.println("진입되는지 확인");
        System.out.println("받은 위도: " + latitude);
        System.out.println("받은 경도: " + longitude);

        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        String userId = currentUser.getUserId();
        UserDTO user = service.login(userId, currentPassword);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호를 다시 확인해주세요.");
        }

        user.setUserNick(nickName);
        if (newPassword != null && !newPassword.isEmpty()) {
            user.setUserPasswd(newPassword);
        }

        if (address != null && !address.isEmpty()) {
            String fullAddress = address;
            if (extraAddress != null && !extraAddress.isEmpty()) {
                fullAddress += " " + extraAddress;
            }
            if (detailAddress != null && !detailAddress.isEmpty()) {
                fullAddress += " " + detailAddress;
            }
            user.setUserAddress(fullAddress);
        }

        if (latitude != null) {
            user.setLatitude(latitude);
        }
        if (longitude != null) {
            user.setLongitude(longitude);
        }

        if (profileImage != null && !profileImage.isEmpty()) {
            // 파일 저장 로직을 추가해야 합니다.
        }

        service.updateUser(user);
        session.setAttribute("user", user);

        return ResponseEntity.ok().body("변경 완료");
    }
    
    
    
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData, HttpSession session, HttpServletRequest request) {
        String id = loginData.get("id");
        String passwd = loginData.get("passwd");
        
        UserDTO dto = service.login(id, passwd);
        
        if (dto == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid ID or password.");
        } else {
            
            session.invalidate();
            HttpSession newSession = request.getSession(true);
            newSession.setAttribute("user", dto);
            return ResponseEntity.ok().body(dto); // 로그인 성공 시 사용자 정보를 반환
        }
    }
    
    
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        System.out.println("진입 확인부터");
        session.invalidate();
        return ResponseEntity.ok("로그아웃 성공");
    }
    
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        try {
            service.insertUser(userDTO);
            return ResponseEntity.ok("회원가입에 성공하셨습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 실패: " + e.getMessage());
        }
    }
    
    @GetMapping("/members")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = service.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    
    
    @PostMapping("/find-id")
    public ResponseEntity<String> findIdByEmail(@RequestBody Map<String, String> emailData) {
        String email = emailData.get("email");
        UserDTO user = service.findUserByEmail(email);
        
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("아이디를 찾을 수 없습니다.");
        } else {
            String userId = user.getUserId();
            String maskedUserId = maskUserId(userId);
            return ResponseEntity.ok(maskedUserId);
        }
    }

    @PostMapping("/find-password")
    public ResponseEntity<String> findPasswordByIdAndEmail(@RequestBody Map<String, String> userData) {
        String userId = userData.get("id");
        String email = userData.get("email");
        UserDTO user = service.findUserByIdAndEmail(userId, email);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("아이디 또는 이메일이 일치하지 않습니다.");
        } else {
            String tempPassword = generateTempPassword();
            service.updateUserPassword(userId, tempPassword);
            sendEmailWithTempPassword(email, tempPassword);
            return ResponseEntity.ok("임시 비밀번호가 이메일로 전송되었습니다.");
        }
    }

    private String generateTempPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    private void sendEmailWithTempPassword(String email, String tempPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("동네방네 임시비밀번호 발급");
        message.setText("임시 비밀번호: " + tempPassword +" 입니다. 이제 임시비밀번호를 이용해서 로그인후 마이페이지에서 비밀번호를 바꿔주세요!");
        mailSender.send(message);
    }

    private String maskUserId(String userId) {
        if (userId.length() <= 3) return userId;
        int length = userId.length();
        return userId.substring(0, length - 3) + "***";
    }

    
    
    
    
    
    
    
    
    
    
 
    

    
    
    
    
    


    
    
    
}




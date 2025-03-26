package com.example.demo.member.model.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.demo.member.model.vo.Friend;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import com.example.demo.member.model.mapper.MemberMapper;
import com.example.demo.member.model.vo.Member;
import com.example.demo.member.model.vo.ProfileImage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper mapper;
    private final BCryptPasswordEncoder bcrypt;

    // 이메일 중복 확인
    public int checkEmail(String email) {
        return mapper.checkEmail(email);
    }

    // 이메일로 아이디 찾기
    public String findId(String email) {
        return mapper.findId(email);
    }

    // 이메일 아이디 일치 여부 확인
    public int confirmIdEmail(Member m) {
        return mapper.confirmIdEmail(m);
    }

    // 비밀번호 재설정
    public int resetPwd(Member m) {
        return mapper.resetPwd(m);
    }

    // 로그인 처리
    public Member login(String memberId, String memberPwd) {
        Member member = mapper.login(memberId); 
        if (member != null && bcrypt.matches(memberPwd, member.getMemberPwd())) {
            return member;
        }
        return null;
    }

    // 회원가입 처리 (성공 여부 반환)
    public int insertMember(Member member) {
        if (member.getMemberBirth() == 0) {  
            throw new IllegalArgumentException("생년월일은 필수 입력 값입니다.");
        }

        // 비밀번호 암호화 후 저장
        member.setMemberPwd(bcrypt.encode(member.getMemberPwd()));

        // 회원 정보 DB 저장 후 결과 반환
        return mapper.insertMember(member);
    }
    
    // 아이디 중복 확인
    public boolean isMemberIdDuplicated(String memberId) {
        int count = mapper.checkMemberId(memberId);
        System.out.println("🟡 checkMemberId: " + memberId + " → count = " + count);
        return count > 0;
    }
    
    public boolean checkIdAvailable(String memberId) {
        int count = mapper.checkMemberId(memberId);
        System.out.println("🟡 checkMemberId: " + memberId + " → count = " + count);
        return count == 0;
    }
    
    // 닉네임 중복 확인
    public boolean isMemberNicknameDuplicated(String memberNickname) {
        return mapper.checkMemberNickname(memberNickname) > 0;
    }

    // 전화번호 중복 확인
    public boolean isMemberPhoneDuplicated(String memberPhone) {
        return mapper.checkMemberPhone(memberPhone) > 0;
    }

    // 이메일 중복 확인
    public boolean isMemberEmailDuplicated(String memberEmail) {
        return mapper.checkMemberEmail(memberEmail) > 0;
    }
    
    //친구 목록 가져오기(번호만)
	public ArrayList<Integer> selectFriendNumbers(Member loginMember) {
		return mapper.selectFriendNumbers(loginMember);
	}

	// 내가 보낸 친구 요청 목록 가져오기(번호만)
	public ArrayList<Integer> selectRequestSent(int memberNo) {
		return mapper.selectRequestSent(memberNo);
	}
	
	// 내가 받은 친구 요청 목록 가져오기(번호만)
	public ArrayList<Integer> selectRequestReceived(int memberNo) {
		return mapper.selectRequestReceived(memberNo);
	}

	// 친구 삭제, 거절, 요청 취소
	public int deleteFriend(HashMap<String, Integer> map) {
		return mapper.deleteFriend(map);
	}
	
	// 친구 수락
	public int approveRequest(HashMap<String, Integer> map) {
		return mapper.approveRequest(map);
	}
	
	// 회원 찾기 (친구 추가)
	public ArrayList<Member> findMember(Map<String, String> searchMap) {
		return mapper.findMember(searchMap);
	}
	
	// 친구관계 확인
	public HashMap<String, String> friendCheck(HashMap<String, Integer> map) {
		return mapper.friendCheck(map);
	}
	
	//친구 요청
	public int requestFriend(HashMap<String, Integer> map) {
		return mapper.requestFriend(map);
	}
	
	//차단 여부 확인
	public int blockCheck(HashMap<String, Integer> map) {
		return mapper.blockCheck(map);
	}
	
	//회원 차단
	public int blockMember(HashMap<String, Integer> map) {
		return mapper.blockMember(map);
	}
	
	//회원 정보 수정
	public int editMemberInfo(HashMap<String, String> map) {
		return mapper.editMemberInfo(map);
	}
	
	public Member selectMember(int memberNo) {
		return mapper.selectMember(memberNo);
	}
	
	//프사 저장
	public boolean changeProfileImg(int memberNo, MultipartFile image) {
		String oldName = image.getOriginalFilename();
		String type = oldName.substring(oldName.indexOf("."));
		String path = "c:\\RealMan";
		String path2 = "profile-images";
		
		File dir = new File(path);
		File folder = new File(path+"\\"+path2);	
		if (!dir.exists()) {
			dir.mkdir();
		}
		if (!folder.exists()) {
			folder.mkdir();
		}
		
		for(File f :folder.listFiles()) {
			String name1 = f.getName().substring(0, f.getName().indexOf("."));
			if (name1.equals(memberNo+"")) {
				f.delete();
			}
		}
		
		try {
			image.transferTo(new File(folder+"\\"+memberNo+type));
			
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("memberNo", memberNo+"");
			map.put("col", "profile_image");
			map.put("val", memberNo+type);
			
			int result = mapper.editMemberInfo(map);
			
			return result==1? true : false;
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}


	public boolean saveOrUpdateProfileImage(ProfileImage profileImage) {
		ProfileImage existingImage = mapper.getProfileImage(profileImage.getMcdNo());
        if (existingImage == null) {
            return mapper.insertProfileImage(profileImage) > 0;
        } else {
        	profileImage.setImgNo(existingImage.getImgNo());
            return mapper.updateProfileImage(profileImage) > 0;
        }
		
	}

	public ProfileImage selectImage(int memberNo) {
		return mapper.selectImage(memberNo);
	}

	public ArrayList<Member> selectMembers(ArrayList<Integer> memberNumberList) {
		return mapper.selectMembers(memberNumberList);
	}
	public int getMemberNo(String memberId) {
		return mapper.getMemberNo(memberId);
	}

	public Member selectMemberNo(String sender) {
		return mapper.selectMemberNo(sender);
	}

    public ArrayList<Friend> friendList(int memberNo) {
		return	mapper.friendList(memberNo);
    }

	public String getProfileImgUrlbyNickname(String memberNickname) {
		return mapper.getProfileImgUrlbyNickname(memberNickname);
	}
}

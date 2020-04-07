package jpabook.jpashop.api;


import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /**
     * Entity 를 바로 받는 경우! 나중에 망함! Member는 Entity 라 가끔 변경되는데 Call 하는 곳에서 바로 오류남
     * v2는 별도의DTO 를 이용했기 때문에 Entity가 변경되도 DTO는 안변경되게 할 수 있겠지
     * @param member
     * @return
     */
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        // @RequestBody 이게 json 데이터를 Member 에 자동으로 셋팅해줌
        // @Valid 이게 있음 Entity 의 NotEmtpty 등 어노테이션등을 Validation 해줌

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }


    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemverV2(@RequestBody @Valid CreateMemberRequest request){

        Member member = new Member();
        member.setName(request.getName()); // 이부분에서 Entity 가 변경되도 매핑 할 수 있지

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }


    @Data
    static class CreateMemberRequest{
        private String name;

        public CreateMemberRequest(String name){
            this.name = name;
        }
    }

    @Data
    static class CreateMemberResponse{
        private Long id;

        public CreateMemberResponse(Long id){
            this.id = id;
        }
    }

}

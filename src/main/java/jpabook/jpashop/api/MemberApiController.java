package jpabook.jpashop.api;


import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> memberV1(){
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result memberV2(){
        List<Member> findMembers =  memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(member -> new MemberDto(member.getName()))
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String name;
    }


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
        @NotEmpty
        private String name;

        public CreateMemberRequest() {
        }

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

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
        @PathVariable("id") Long id,
        @RequestBody @Valid UpdateMemberRequest request
    ){
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);

        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {

        public UpdateMemberResponse() {}

        private Long id;
        private String name;
    }

}

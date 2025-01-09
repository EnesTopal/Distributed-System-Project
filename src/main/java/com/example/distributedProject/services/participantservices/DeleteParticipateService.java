package com.example.distributedProject.services.participantservices;

import com.example.distributedProject.Command;
import com.example.distributedProject.model.Participant;
import com.example.distributedProject.services.ParticipantRepository;
import com.example.distributedProject.services.UserCheckService;
import com.mysql.cj.x.protobuf.MysqlxCursor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteParticipateService implements Command<Integer,String> {
    private ParticipantRepository participantRepository;
    private final UserCheckService userCheckService;

    public DeleteParticipateService(UserCheckService userCheckService, ParticipantRepository participantRepository) {
        this.userCheckService = userCheckService;
        this.participantRepository = participantRepository;
    }

    @Override
    public ResponseEntity<String> execute (Integer id){

        Integer participiantId = participantRepository.findById(id).get().getUser().getUuid();
        ResponseEntity<Void> userCheckResponse = userCheckService.sameUserCheck(participiantId);
        if (!userCheckResponse.getStatusCode().is2xxSuccessful()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Yetkisiz işlem: Bu kullanıcı bu işlemi gerçekleştiremez.");
        }

        Optional<Participant> participantOptional = participantRepository.findById(id);
        if (participantOptional.isPresent()){
            participantRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Katılım başarıyla silindi");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Katılım bulunamadı");
    }
}

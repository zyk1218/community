package life.isip.community.service;


import life.isip.community.dto.QuestionDTO;
import life.isip.community.mapper.QuestionMapper;
import life.isip.community.mapper.UserMapper;
import life.isip.community.model.QuestionModel;
import life.isip.community.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
  * @author remember
  * @date 2020/1/29 20:11
  */


@Service
public class QuestionService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionMapper questionMapper;

    public List<QuestionDTO> list() {
        List<QuestionModel> questionModels = questionMapper.list();
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (QuestionModel questionModel : questionModels) {
            UserModel user = userMapper.findUserByCreator(questionModel.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(questionModel,questionDTO);
            questionDTO.setUserModel(user);
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;
    }
}

package com.bolton.globalhotelhub.service.impl;

import com.bolton.globalhotelhub.dto.ResponseDTO;
import com.bolton.globalhotelhub.dto.request.FilterHotelRequestDTO;
import com.bolton.globalhotelhub.repository.SearchHotelHistoryRepository;
import com.bolton.globalhotelhub.repository.UserRepository;
import com.bolton.globalhotelhub.service.HotelService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class HotelServiceImpl implements HotelService {
    private static final Logger LOGGER = LogManager.getLogger(HotelServiceImpl.class);
    private final UserRepository userRepository;
    private final SearchHotelHistoryRepository searchHotelHistoryRepository;

    @Autowired
    public HotelServiceImpl(UserRepository userRepository, SearchHotelHistoryRepository searchHotelHistoryRepository) {
        this.userRepository = userRepository;
        this.searchHotelHistoryRepository = searchHotelHistoryRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<ResponseDTO> getHotelByModel(FilterHotelRequestDTO filterHotelRequestDTO) {
       try {


       }catch (Exception e){
           LOGGER.error("Function : getHotelByModel  : " + e.getMessage());
           throw e;
       }
    }
}

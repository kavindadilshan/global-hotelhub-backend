package com.bolton.globalhotelhub.service.impl;

import com.bolton.globalhotelhub.dto.response.SearchHotelHistoryResponseDTO;
import com.bolton.globalhotelhub.entity.SearchHotelHistory;
import com.bolton.globalhotelhub.entity.Users;
import com.bolton.globalhotelhub.exception.GlobalHotelHubServiceException;
import com.bolton.globalhotelhub.repository.SearchHotelHistoryRepository;
import com.bolton.globalhotelhub.repository.UserRepository;
import com.bolton.globalhotelhub.service.SearchHotelHistoryService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.bolton.globalhotelhub.constant.ErrorCodeConstant.RESOURCE_NOT_FOUND;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SearchHotelHistoryServiceImpl implements SearchHotelHistoryService {

    private final SearchHotelHistoryRepository searchHotelHistoryRepository;
    private final UserRepository userRepository;

    private static final Logger LOGGER = LogManager.getLogger(SearchHotelHistoryServiceImpl.class);

    public SearchHotelHistoryServiceImpl(SearchHotelHistoryRepository searchHotelHistoryRepository, UserRepository userRepository) {
        this.searchHotelHistoryRepository = searchHotelHistoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<SearchHotelHistoryResponseDTO> getSearchHotelHistoryByUser(Long userId) {
        Optional<Users> user = userRepository.findById(userId);

        if(!user.isPresent())
            throw new GlobalHotelHubServiceException(RESOURCE_NOT_FOUND, "User not found");

        List<SearchHotelHistory> result = searchHotelHistoryRepository.getSearchHotelHistoriesByUsersId(userId);

        List<SearchHotelHistoryResponseDTO> historyList = new ArrayList<>();

        for (SearchHotelHistory searchHotelHistory:result){
            SearchHotelHistoryResponseDTO searchHotelHistoryResponseDTO = new SearchHotelHistoryResponseDTO();
            searchHotelHistoryResponseDTO.setUserId(searchHotelHistory.getUsers().getId());
            searchHotelHistoryResponseDTO.setFullName(searchHotelHistory.getUsers().getName());
            searchHotelHistoryResponseDTO.setLocation(searchHotelHistory.getLocation());
            searchHotelHistoryResponseDTO.setCheckin(searchHotelHistory.getCheckin());
            searchHotelHistoryResponseDTO.setCheckout(searchHotelHistory.getCheckout());
            searchHotelHistoryResponseDTO.setRooms(searchHotelHistory.getRooms());
            searchHotelHistoryResponseDTO.setAdults(searchHotelHistory.getAdults());
            searchHotelHistoryResponseDTO.setChild(searchHotelHistory.getChild());
            searchHotelHistoryResponseDTO.setMaxPrice(searchHotelHistory.getMaxPrice());
            searchHotelHistoryResponseDTO.setMinPrice(searchHotelHistory.getMinPrice());

            historyList.add(searchHotelHistoryResponseDTO);

        }

        return historyList;

    }

    @Override
    public List<SearchHotelHistoryResponseDTO> getAllSearchHotelHistory() {

        try {
            List<SearchHotelHistory> result = searchHotelHistoryRepository.findAll();
            List<SearchHotelHistoryResponseDTO> historyList = new ArrayList<>();

            for (SearchHotelHistory searchHotelHistory:result){
                SearchHotelHistoryResponseDTO searchHotelHistoryResponseDTO=new SearchHotelHistoryResponseDTO();
                searchHotelHistoryResponseDTO.setUserId(searchHotelHistory.getUsers().getId());
                searchHotelHistoryResponseDTO.setFullName(searchHotelHistory.getUsers().getName());
                searchHotelHistoryResponseDTO.setLocation(searchHotelHistory.getLocation());
                searchHotelHistoryResponseDTO.setCheckin(searchHotelHistory.getCheckin());
                searchHotelHistoryResponseDTO.setCheckout(searchHotelHistory.getCheckout());
                searchHotelHistoryResponseDTO.setRooms(searchHotelHistory.getRooms());
                searchHotelHistoryResponseDTO.setAdults(searchHotelHistory.getAdults());
                searchHotelHistoryResponseDTO.setChild(searchHotelHistory.getChild());
                searchHotelHistoryResponseDTO.setMaxPrice(searchHotelHistory.getMaxPrice());
                searchHotelHistoryResponseDTO.setMinPrice(searchHotelHistory.getMinPrice());

                historyList.add(searchHotelHistoryResponseDTO);
            }

            return historyList;

        }catch (Exception e){
            LOGGER.error("Function : getAllSearchHistory  : " + e.getMessage());
            throw e;
        }

    }
}

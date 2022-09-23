package com.shop.projectlion.api.adminitem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.projectlion.api.adminitem.controller.AdminItemController;
import com.shop.projectlion.api.adminitem.dto.UpdateItemDto;
import com.shop.projectlion.api.adminitem.service.AdminItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.shop.projectlion.domain.item.model.enumclass.ItemSellStatus.SELL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.RequestEntity.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UpdateItemControllerTest {

    @InjectMocks
    AdminItemController adminItemController;

    @Mock
    AdminItemService adminItemService;

    @Spy
    ObjectMapper objectMapper;

    MockMvc mockMvc;

    UpdateItemDto.Request updateRequestDto;

    UpdateItemDto.Response updateResponseDto;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminItemController)
            .build();
    }

    @BeforeEach
    void createUpdateItemRequest() {
        updateRequestDto = UpdateItemDto.Request.builder()
            .itemId(2L)
            .itemName("남방02")
            .itemDetail("남방, 재고 1000개, 사진 3개, 업데이트 테스트")
            .price(18000)
            .stockNumber(1000)
            .itemSellStatus(SELL)
            .deliveryId(99999L)
            .build();

        updateResponseDto = UpdateItemDto.Response.builder()
            .itemId(2L)
            .itemName("남방02")
            .itemDetail("남방, 재고 1000개, 사진 3개, 업데이트 테스트")
            .price(18000)
            .stockNumber(1000)
            .itemSellStatus(SELL)
            .deliveryId(99999L)
            .build();
    }

    @Test
    @DisplayName("상품 수정 성공 테스트")
    void updateITem() throws Exception{
        Long itemId = 2L;
        String updateItem = objectMapper.writeValueAsString(updateRequestDto);

        given(adminItemService.getUpdateResponseDto(any())).willReturn(updateResponseDto);

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.patch("/api/admin/items/{itemId}",itemId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(updateItem))
            ;

        // then
        resultActions.andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.itemId").value(updateResponseDto.getItemId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.itemName").value(updateResponseDto.getItemName()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.itemDetail").value(updateResponseDto.getItemDetail()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(updateResponseDto.getPrice()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.stockNumber").value(updateResponseDto.getStockNumber()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.itemSellStatus").value(updateResponseDto.getItemSellStatus().name()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.deliveryId").value(updateResponseDto.getDeliveryId()))
            ;

        Assertions.assertEquals(itemId, updateRequestDto.getItemId());
    }





}

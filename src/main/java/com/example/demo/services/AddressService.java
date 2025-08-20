package com.example.demo.services;

import com.example.demo.Entity.Address;
import com.example.demo.payloads.AddressDTO;

import java.util.List;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO);

    List<AddressDTO> getAddresses();

    AddressDTO getAddress(Long addressId);

    AddressDTO updateAddress(Long addressId, Address address);

    String deleteAddress(Long addressId);
}

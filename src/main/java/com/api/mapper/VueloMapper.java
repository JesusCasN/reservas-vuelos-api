package com.api.mapper;

import com.api.dto.VueloDTO;
import com.api.model.Vuelo;

public class VueloMapper {

    public static VueloDTO toDTO(Vuelo vuelo) {
        if(vuelo == null){
            return null;
        }
        return new VueloDTO(
                vuelo.getId(),
                vuelo.getAerolinea(),
                vuelo.getOrigen(),
                vuelo.getDestino(),
                vuelo.getFechaSalida(),
                vuelo.getFechaLlegada(),
                vuelo.getPrecio(),
                vuelo.getAsientosDisponibles()
        );
    }

    public static Vuelo toEntity(VueloDTO vueloDTO) {
        if(vueloDTO == null){
            return null;
        }
        Vuelo vuelo = new Vuelo();
        actualizarDesdeDTO(vuelo, vueloDTO);
        return vuelo;
    }

    public static void actualizarDesdeDTO(Vuelo vuelo, VueloDTO vueloDTO) {
        vuelo.setAerolinea(vueloDTO.getAerolinea());
        vuelo.setOrigen(vueloDTO.getOrigen());
        vuelo.setDestino(vueloDTO.getDestino());
        vuelo.setFechaSalida(vueloDTO.getFechaSalida());
        vuelo.setFechaLlegada(vueloDTO.getFechaLlegada());
        vuelo.setPrecio(vueloDTO.getPrecio());
        vuelo.setAsientosDisponibles(vueloDTO.getAsientosDisponibles());
    }



}

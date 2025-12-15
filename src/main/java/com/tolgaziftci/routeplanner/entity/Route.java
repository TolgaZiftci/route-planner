package com.tolgaziftci.routeplanner.entity;

import com.tolgaziftci.routeplanner.dao.TransportationDao;

import java.util.List;

public record Route(List<TransportationDao> transportations) {
}

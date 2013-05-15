package com.unrc.app.models;

import org.javalite.activejdbc.Model;

@Table("realEstates_Owners")
public class RealEstatesOwners extends Model {
  static{
      validatePresenceOf("owner_id","realEstate_id");
  }
}

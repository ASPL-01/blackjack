package com.galvanize.repositories;

import com.galvanize.entities.Game;
import com.galvanize.enums.Outcome;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IGameRepository extends CrudRepository<Game,Integer> {
    @Query(value = "SELECT COUNT(*) FROM games WHERE outcome = :outcome", nativeQuery = true)
    public int findNumOfOutcomes(@Param("outcome") String outcome);
}

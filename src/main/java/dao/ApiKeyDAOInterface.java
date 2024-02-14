package dao;

import entidades.ApiKeys;
import entidades.Exercises;

import java.util.List;

public interface ApiKeyDAOInterface {

    ApiKeys createApiKey(ApiKeys apiKeys);

    ApiKeys getApiKey(String apikey);

    List<ApiKeys> showAll();

    boolean deleteById(Long id);

    ApiKeys modifyByID(ApiKeys apiKeys);

}

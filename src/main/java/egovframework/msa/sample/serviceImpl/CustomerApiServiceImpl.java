package egovframework.msa.sample.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import egovframework.msa.sample.service.CustomerApiService;

@Service
public class CustomerApiServiceImpl implements CustomerApiService {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	@HystrixCommand(fallbackMethod = "getCustomerDetailFallback") // 서비스가 에러 또는 지연될 경우 곧 바로 Fallback 메서드 호출하여 에러 전파 방지
	public String getCustomerDetail(String customerId) {
		return restTemplate.getForObject("http://customer/customers/" + customerId, String.class);
	}

	public String getCustomerDetailFallback(String customerId, Throwable ex) {
		System.out.println("Error:" + ex.getMessage());
		return "고객정보 조회가 지연되고 있습니다.";
	}

}

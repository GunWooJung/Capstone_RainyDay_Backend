package com.cos.capstone.nouse;


/*
@RestController
public class RouteController {

	
	@Autowired
	private LocationService routeService;

	@GetMapping("/route/load") // load schedule
	public ResponseEntity<List<Location>> loadRoute(@RequestParam("scheduleId") Long id) {

		List<Location> list = routeService.loadRoute(id);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	@PostMapping("/route/create") // schedule 생성 public ResponseEntity<String>
	public ResponseEntity<String> createRoute(@RequestParam("scheduleId") Long id,
			@RequestBody RouteRequestDto routeRequestDto) {

		ResultCodeEnum routeResultCode = routeService.createRoute(id, routeRequestDto);

		if (routeResultCode == ResultCodeEnum.SUCCESS) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	
}
*/
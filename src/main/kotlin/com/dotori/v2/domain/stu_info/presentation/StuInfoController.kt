    @GetMapping("/search")
    fun searchStudent(searchRequestDto: SearchRequestDto): List<SearchStudentListResDto> =
        searchStudentService.execute(searchRequestDto)

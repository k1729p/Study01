@echo on
@set SITE=http://localhost:8080
@set CURL=c:\tools\curl-7.58.0\bin\curl.exe
@set CURL=%CURL% -g -i -H "Accept: application/json" -H "Content-Type: application/json"
@set HR_YELLOW=@powershell -Command Write-Host "----------------------------------------------------------------------" -foreground "Yellow"
@set HR_RED=@powershell    -Command Write-Host "----------------------------------------------------------------------" -foreground "Red"

@set DEP_ID=12345
@set EMP_ID=67890

:create
%HR_YELLOW%
@powershell -Command Write-Host "CREATE" -foreground "Green"
%CURL% -d {\"id\":%DEP_ID%,\"name\":\"D-Name-CREATE\"} -X POST "%SITE%/departments"
@echo.
%CURL% -d {\"id\":%EMP_ID%,\"firstName\":\"EF-Name-CREATE\",\"lastName\":\"EL-Name-CREATE\",\"title\":\"developer\"} ^
	-X POST "%SITE%/departments/%DEP_ID%/employees"
@echo.

:read-created
%HR_YELLOW%
@powershell -Command Write-Host "READ [created] by id" -foreground "Green"
%CURL% "%SITE%/departments/%DEP_ID%"
@echo.
%CURL% "%SITE%/departments/%DEP_ID%/employees/%EMP_ID%"
@echo.&pause

:update-with-patch
%HR_YELLOW%
@powershell -Command Write-Host "UPDATE with PATCH by id" -foreground "Green"
%CURL% -d {\"name\":\"D-Name-PATCH\"} -X PATCH "%SITE%/departments/%DEP_ID%"
@echo.
%CURL% -d {\"firstName\":\"EF-Name-PATCH\",\"lastName\":\"EL-Name-PATCH\"} ^
	-X PATCH "%SITE%/departments/%DEP_ID%/employees/%EMP_ID%"
@echo.

:read-updated
%HR_YELLOW%
@powershell -Command Write-Host "READ [updated] by id " -foreground "Green"
%CURL% "%SITE%/departments/%DEP_ID%"
@echo.
%CURL% "%SITE%/departments/%DEP_ID%/employees/%EMP_ID%"
@echo.&pause

:delete
%HR_YELLOW%
@powershell -Command Write-Host "DELETE by id" -foreground "Green"
%CURL% -X DELETE "%SITE%/departments/%DEP_ID%/employees/%EMP_ID%"
@echo.
%CURL% -X DELETE "%SITE%/departments/%DEP_ID%"
@echo.

:read-deleted
%HR_YELLOW%
@powershell -Command Write-Host "READ [deleted] by id " -foreground "Green"
%CURL% "%SITE%/departments/%DEP_ID%"
@echo.
%CURL% "%SITE%/departments/%DEP_ID%/employees/%EMP_ID%"
@echo.

:finish
%HR_RED%
@powershell -Command Write-Host "FINISH" -foreground "Red"
pause
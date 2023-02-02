@echo on
@set SITE=http://localhost:8080
@set CURL=c:\tools\curl-7.58.0\bin\curl.exe
@set CURL=%CURL% -g -i -H "Accept: application/json" -H "Content-Type: application/json"
@set HR_YELLOW=@powershell -Command Write-Host "----------------------------------------------------------------------" -foreground "Yellow"
@set HR_RED=@powershell    -Command Write-Host "----------------------------------------------------------------------" -foreground "Red"

@set DEP_ID=1
@set EMP_ID=101

%HR_YELLOW%
@powershell -Command Write-Host "Load sample dataset" -foreground "Green"
%CURL% "%SITE%/loadSampleDataset"
@echo.

:get-one-department
%HR_YELLOW%
@powershell -Command Write-Host "GET one department" -foreground "Green"
%CURL% "%SITE%/departments/%DEP_ID%"
@echo.

:get-one-employee
%HR_YELLOW%
@powershell -Command Write-Host "GET one employee" -foreground "Green"
%CURL% "%SITE%/departments/%DEP_ID%/employees/%EMP_ID%"
@echo.&pause

:get-all-departments
%HR_YELLOW%
@powershell -Command Write-Host "GET all departments" -foreground "Green"
%CURL% "%SITE%/departments"
@echo.

:get-all-employees
%HR_YELLOW%
@powershell -Command Write-Host "GET all employees" -foreground "Green"
%CURL% "%SITE%/departments/%DEP_ID%/employees"
@echo.

:finish
%HR_RED%
@powershell -Command Write-Host "FINISH" -foreground "Red"
pause
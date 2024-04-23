@echo off
setlocal enabledelayedexpansion

set output=Android_project.txt
set ignore=_site,MasterDefaultPosts,.git,.jekyll-cache,.vscode,_sass, defaultJekillPosts,_pages,defaultJekillPosts,DiscardedPagesForNow,PagesTORestructure,IgnorePosts,hidden
set extensions=.java

set progress=1  :: set to 1 to show progress, set to 0 to hide progress

if exist %output% del %output%

for %%e in (%extensions%) do (
  for /R "%cd%" %%f in (*%%e) do (
    if exist "%%f" (
      set skip=
      for %%i in (%ignore%) do (
        if "!skip!"=="" echo %%f | findstr /i "\\%%i\\" >nul && set skip=1
      )
      
      if "!skip!"=="" (
        findstr "^" "%%f" >nul
        if errorlevel 1 (
          echo Empty file: %%f
        ) else (
          if !progress!==1 echo Adding: %%f 
          echo /* >> %output%
          set "relative_path=%%f"
          setlocal enabledelayedexpansion
          set "relative_path=!relative_path:%cd%\=.\!"
          echo File: !relative_path! >> "%output%"    
          endlocal
          echo /* >> %output%
          type "%%f" >> %output%
        )
      )
    )
  )
)

echo Done!
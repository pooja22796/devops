cmake_minimum_required(VERSION 3.10)
project(MyFirmware C)

set(CMAKE_C_STANDARD 11)

# Optional: Find objcopy (used to generate binary file)
find_program(CMAKE_OBJCOPY NAMES objcopy)

add_executable(myfirmware.elf
    src/main.c
)

# Generate .bin after building .elf
add_custom_command(TARGET myfirmware.elf POST_BUILD
    COMMAND ${CMAKE_OBJCOPY} -O binary myfirmware.elf myfirmware.bin
    COMMENT "Generating .bin from .elf"
)

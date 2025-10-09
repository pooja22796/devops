#!/bin/bash
set -e

cd "$(dirname "$0")"
mkdir -p build
cd build

echo "📦 Running CMake..."
time cmake ..

echo "⚙️ Building with Make (parallel)..."
time make -j$(nproc)

echo "✅ Build output:"
ls -lh *.elf *.bin

# 👇 NEW SECTION: Create output folder and copy .bin files
mkdir -p output
cp -v *.bin output/

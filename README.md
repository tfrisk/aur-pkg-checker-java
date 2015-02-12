# AUR package checker

A command line tool for checking local packages installed from AUR (Arch User Repository).

Java version of https://github.com/tfrisk/aur-pkg-checker (which is written in Ruby).

## Requirements

* Arch Linux
* Java runtime
* Apache ant build tool

## Compilation

Either load the project to eclipse and build it there or use <code>ant</code> from the command line.

## Usage

The program checks your local installed packages (with <code>pacman -Qm</code>) and then performs a check againts the latest versions in AUR (https://aur.archlinux.org/). The version comparison is done with <code>vercmp</code> which guarantees compatibility with <code>pacman</code>.

Example:
<pre>
$ java -jar AurPkgChecker.jar 
Current time is 2015-02-11 11:49:07 +0200
Checking package versions
libudev.so.0: 0.1.1-2 => OK
lighttable: 0.7.2-1 => OK
sublime-text: 2.0.2-1 => new version available: 2.0.2-4
</pre>

In this example the user has 3 installed packages. Two of these packages are up-to-date, and one has a newer version available.

The program can be run with regular user privileges.


By default the script will download updated packages to a <code>build</code> directory where the script is ran. Automatic download can be disabled with <code>'-c'</code> command line option.

## TODO

* Log actions
* Ignored package list (don't want to update certain packages)
* Install new packages


Copyright (C) 2015 Teemu Frisk
Distributed under MIT License

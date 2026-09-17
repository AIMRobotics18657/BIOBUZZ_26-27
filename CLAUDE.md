# BIOBUZZ 2026-27

FTC robot code built on **Pedro Pathing 3.0**. Read this before changing anything.

| Component | Version |
|---|---|
| FTC SDK | 11.2.1 |
| `com.pedropathing:core` / `:revhub` | 3.0.0 |
| `com.pedropathing:tuning` | 1.0.0 |
| AGP / Gradle | 8.13.2 / 9.1.0 |

## Build and verify

There is no JDK on `PATH`; use the one bundled with Android Studio.

```sh
export JAVA_HOME="$HOME/Applications/Android Studio.app/Contents/jbr/Contents/Home"
./gradlew :TeamCode:compileDebugJavaWithJavac
```

Do not use `--offline`; `aapt2` is fetched from the network on a cold build. The `source/target 8`
deprecation warnings are pre-existing and expected.

**Never report work as done without a successful compile.** There is no test suite and no way to
run OpModes off-robot, so compilation is the only automated signal available.

## Architecture

```
TeamCode/src/main/java/org/firstinspires/ftc/teamcode/
├── config/
│   ├── ConfigInfo.java        Robot-dependent values: RC config names, geometry, tuner output
│   └── GamepadSettings.java   Driver preferences and control bindings
├── input/
│   ├── InputModification.java Stateless shaping: deadzone, expo, clamp, scale, SlewLimiter
│   └── InputHandler.java      Raw gamepad -> shaped DrivePowers + latched actions
├── opmode/
│   └── Teleop.java            LinearOpMode driving Follower.manual()
└── pedro/
    ├── Constants.java         Wires ConfigInfo into the Pedro follower. No literals.
    ├── Tuning.java            Tuner registration
    └── procedures/            Pedro autotune procedures
```

Dependency direction is strictly one way: `opmode` -> `input` -> `config`, and `pedro` -> `config`.
Nothing in `config` or `input` may import an OpMode.

## Rules

**Verify library APIs against the real sources, never from memory.** Pedro 3.0 is a rewrite and
differs sharply from 2.x — `Follower` is constructed `(Localizer, Drivetrain, Algorithm)`, config is
`ConfigVar`/`Configuration` lambdas, and `FollowerConstants`/`setConstants` no longer exist. To
check a signature:

```sh
# revhub and tuning ship sources in the Gradle cache
find ~/.gradle/caches -ipath "*pedropathing*" -name "*-sources.jar"
# core does not; pull it from Maven Central
curl -sSLO https://repo1.maven.org/maven2/com/pedropathing/core/3.0.0/core-3.0.0-sources.jar
```

**`ConfigInfo` is the single source of truth for anything robot-dependent.** Hardware names,
directions, offsets and tuner output live there and nowhere else. `Constants.java` contains no
numeric or string literals — it only wires `ConfigInfo` into Pedro configs.

**Placeholder discipline.** Values that are only knowable once the robot is built and tuned are set
to `ConfigInfo.PLACEHOLDER`, never to an invented but plausible number. A guess that looks like a
measurement is worse than an obvious stand-in. When the type forces a real value (enums such as
motor direction or pod type), pick the uniform/neutral option and say so in the progress log.

Note that `Foresight`'s constructor eagerly calls `naturalForwardDeceleration.get()`, so these
`ConfigVar`s cannot simply be left unset — doing so throws at `Constants.create()` and breaks
teleop init, not just autonomous.

**Comment sparingly.** Explain why, not what. No comment restating the code beside it.

**Ask before inventing subsystems.** This is a starter repo; do not scaffold mechanisms, autonomous
routines, or hardware that has not been described.

## Keep these current

Both of these are part of the work, not an afterthought — a change is not finished until they are
updated in the same commit.

1. **Append to `docs/PROGRESS.md`** after any substantive change: what changed, why, what was
   verified, and what is still a placeholder. Newest entry at the top.
2. **Update the Architecture tree above** whenever a package or top-level file is added, removed, or
   repurposed, so it never drifts from the filesystem.

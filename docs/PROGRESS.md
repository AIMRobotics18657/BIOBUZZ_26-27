# Progress Log

Newest first. One entry per substantive change: what changed, why, what was verified, what is still
a placeholder. See `CLAUDE.md` for the rules this log is part of.

---

## 2026-09-17 — Teleop and input stack scaffolded

**Changed**
- Added `config/ConfigInfo.java`, `config/GamepadSettings.java`, `input/InputModification.java`,
  `input/InputHandler.java`, `opmode/Teleop.java`.
- Filled in `pedro/Constants.java`, which previously returned `null`.
- Added `CLAUDE.md` and this log.

**Why**
Starter repo needed a working teleop and a place for robot-dependent values that is not scattered
through the OpModes. `ConfigInfo` is now the single source of truth; `Constants.java` holds no
literals.

**Verified**
- `:TeamCode:compileDebugJavaWithJavac` succeeds.
- API surface checked against `core-3.0.0-sources.jar` (Maven Central) and the `revhub`/`tuning`
  sources jars in the Gradle cache, not from memory. Two corrections came out of that:
  - The stub comment in `Constants.java` had the argument order wrong. The real signature is
    `Follower(Localizer, Drivetrain, Algorithm)`.
  - `DrivePowers` is `(forward, strafe, turn)`, and `ManualDrive.fieldCentric` maps forward->x,
    strafe->y, so positive strafe is **left**. Stick bindings negate accordingly.
- Gamepad edge-detection methods (`optionsWasPressed`, `shareWasPressed`) confirmed present in
  `RobotCore-11.2.1.aar` rather than assumed.

**Placeholder scope**
`PLACEHOLDER` is reserved for measurements that cannot exist until the robot is physically built.
Anything with a sensible convention is set for real.

| Where | What | Status |
|---|---|---|
| `Drive` | 4 config name strings | set — team convention |
| `Drive` | motor directions, left side `REVERSE` | set — standard mecanum layout; MecanumTuner confirms |
| `Localization` | pod type `goBILDA_4_BAR_POD`, pod directions `FORWARD` | set — conventional; PinpointTuner confirms |
| `Localization` | `X_POD_OFFSET`, `Y_POD_OFFSET` | **PLACEHOLDER** — measured off the built robot |
| `Foresight` | all 17 values | **PLACEHOLDER** — ForesightTuner |

Pedro marks every Foresight parameter `ConfigVar.required()` with no fallback, and publishes no
starting values — AutoTune generates all of them. So there is nothing reasonable to set there until
the robot drives, and inventing numbers would only disguise an untuned robot as a tuned one.

`GamepadSettings` defaults are neutral — expo `0.0`, `SLEW_RATE` infinity, turn scale `1.0` — so the
shaping code is wired but inert until a driver dials it in. These are preferences, not measurements.

**Next**
- Run MecanumTuner, PinpointTuner, ForesightTuner; replace the placeholders above.
- Register tuners in `pedro/Tuning.java` (left untouched).

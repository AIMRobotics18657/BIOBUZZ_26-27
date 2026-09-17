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

**Still placeholder**
| Where | What | Source |
|---|---|---|
| `ConfigInfo.Drive` | 4 config name strings | RC configuration |
| `ConfigInfo.Drive` | 4 motor directions, all `FORWARD` | MecanumTuner |
| `ConfigInfo.Localization` | pod offsets `0.0`, pod directions, pod type | PinpointTuner |
| `ConfigInfo.Foresight` | all 17 values, set to `PLACEHOLDER` | ForesightTuner |

Motor directions, pod directions and pod type are enums, so they carry a real value rather than a
sentinel. All-`FORWARD` is a legal configuration and will not announce itself as wrong — run the
tuners before trusting it. Pod offsets sit at `0.0` rather than `PLACEHOLDER` because a `1.0` offset
would be a silently plausible wrong measurement.

`GamepadSettings` defaults are deliberately neutral — expo `0.0`, `SLEW_RATE` infinity, turn scale
`1.0` — so the shaping code is wired but inert until a driver dials it in.

**Next**
- Run MecanumTuner, PinpointTuner, ForesightTuner; replace the table above.
- Register tuners in `pedro/Tuning.java` (left untouched).

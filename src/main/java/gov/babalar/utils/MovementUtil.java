package gov.babalar.utils;

import gov.babalar.Myth;
import gov.babalar.helpers.Mapper;

public class MovementUtil {

    public static boolean isMoving() {
        return Mapper.getMIForward() != 0.0f || Mapper.getMIStrafe() != 0.0f;
    }


    public static void setSpeed(double moveSpeed, float yaw, double strafe, double forward) {
        if (forward != 0.0) {
            if (strafe > 0.0) {
                yaw += (float)(forward > 0.0 ? -45 : 45);
            } else if (strafe < 0.0) {
                yaw += (float)(forward > 0.0 ? 45 : -45);
            }
            strafe = 0.0;
            if (forward > 0.0) {
                forward = 1.0;
            } else if (forward < 0.0) {
                forward = -1.0;
            }
        }
        if (strafe > 0.0) {
            strafe = 1.0;
        } else if (strafe < 0.0) {
            strafe = -1.0;
        }
        double mx = Math.cos(Math.toRadians(yaw + 90.0f));
        double mz = Math.sin(Math.toRadians(yaw + 90.0f));
        Mapper.setMotionX(forward * moveSpeed * mx + strafe * moveSpeed * mz);
        Mapper.setMotionZ(forward * moveSpeed * mz - strafe * moveSpeed * mx);
    }

    public static void setSpeed(double moveSpeed) {
        MovementUtil.setSpeed(moveSpeed, Mapper.getRotationYaw(), Mapper.getMIStrafe(), Mapper.getMIForward());
    }

    public static double getSpeed(double motionX, double motionZ) {
        return Math.sqrt(motionX * motionX + motionZ * motionZ);
    }

    public static float getSpeed() {
        return (float)MovementUtil.getSpeed(Mapper.getMotionX(), Mapper.getMotionZ());
    }

}

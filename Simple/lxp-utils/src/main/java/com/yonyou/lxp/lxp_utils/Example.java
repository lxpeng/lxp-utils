package com.yonyou.lxp.lxp_utils;


import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.yonyou.lxp.lxp_utils.utils.PicturesSelectUtils;

/**
 * 示例
 */
public class Example {
    //====示例 照片选择 拍照 裁剪===============================

    /**
     * 示例 图片选择
     * @param mContext 上下文
     */
    private void imgSel(Activity mContext) {
        final PicturesSelectUtils picturesSelectUtils=new PicturesSelectUtils(mContext);
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setItems(new String[]{"相册", "相机"}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        picturesSelectUtils.startImagePick();
                        break;
                    case 1:
                        picturesSelectUtils.startTakePhoto();
                        break;
                    default:
                        break;
                }
            }
        });
        builder.setPositiveButton("取消", null);
        builder.show();
    }

    //    @Override
//    public void onActivityResult(final int requestCode, final int resultCode,
//                                 final Intent imageReturnIntent) {
//        if (resultCode != Activity.RESULT_OK)
//            return;
//
//        switch (requestCode) {
//            case PicturesSelectUtils.REQUEST_CODE_GETIMAGE_BYCAMERA:
//                picturesSelectUtils.startActionCrop(picturesSelectUtils.origUri);// 拍照后裁剪
//                break;
//            case PicturesSelectUtils.REQUEST_CODE_GETIMAGE_BYCROP:
//                picturesSelectUtils.startActionCrop(imageReturnIntent.getData());// 选图后裁剪
//                break;
//            case PicturesSelectUtils.REQUEST_CODE_GETIMAGE_BYSDCARD:
//                Toast.makeText(mContext, "上传", Toast.LENGTH_SHORT).show();
//                break;
//        }
//    }

}


